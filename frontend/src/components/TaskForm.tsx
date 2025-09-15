import { useState } from "react";
import { useNavigate } from "react-router-dom";

type TaskFormValues = {
    name: string;
    description?: string;
    tagsInput?: string;
    priority?: "LOW" | "MEDIUM" | "HIGH";
};

const TaskForm = () => {
    const [form, setForm] = useState<TaskFormValues>({
        name: "",
        description: "",
        tagsInput: "",
        priority: undefined,
    });

    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setForm(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        const tags = form.tagsInput
            ? form.tagsInput.split(",").map(tag => tag.trim()).filter(tag => tag)
            : [];

        const payload = {
            name: form.name,
            description: form.description || undefined,
            tags: tags.length > 0 ? tags : undefined,
            priority: form.priority || undefined,
        };

        try {
            const response = await fetch("/api/v1/tasks/new", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(payload),
            });

            if (!response.ok) {
                throw new Error("Failed to create task.");
            }

            setMessage("Task created successfully.");
            navigate("/");
        } catch (err) {
            if (err instanceof Error) {
                setMessage(err.message);
            }
        }
    };

    return (
        <div style={{ maxWidth: "600px", margin: "0 auto", padding: "20px" }}>
            <h2>Create New Task</h2>
            <form onSubmit={handleSubmit} style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
                <input
                    name="name"
                    placeholder="Task name"
                    value={form.name}
                    onChange={handleChange}
                    required
                />
                <input
                    name="description"
                    placeholder="Description (optional)"
                    value={form.description}
                    onChange={handleChange}
                />
                <input
                    name="tagsInput"
                    placeholder="Tags (comma-separated, optional)"
                    value={form.tagsInput}
                    onChange={handleChange}
                />
                <select name="priority" value={form.priority || ""} onChange={handleChange}>
                    <option value="">-- Select Priority (optional) --</option>
                    <option value="LOW">Low</option>
                    <option value="MEDIUM">Medium</option>
                    <option value="HIGH">High</option>
                </select>
                <button type="submit">Create Task</button>
            </form>
            {message && <p style={{ marginTop: "10px", color: "green" }}>{message}</p>}
        </div>
    );
};

export default TaskForm;
