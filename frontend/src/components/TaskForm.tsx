import {useEffect, useState} from "react";
import { useNavigate, useParams } from "react-router-dom";

type TaskFormValues = {
    name: string;
    description?: string;
    tags?: string[];
    priority?: "LOW" | "MEDIUM" | "HIGH";
};

const TaskForm = () => {
    const { id } = useParams();
    const isEditMode = Boolean(id);
    const navigate = useNavigate();

    const [form, setForm] = useState<TaskFormValues>({
        name: "",
        description: "",
        tags: [],
        priority: undefined,
    });

    const [message, setMessage] = useState("");

    useEffect(() => {
        if (isEditMode) {
            const fetchTask = async () => {
                try {
                    const response = await fetch(`/api/v1/tasks/id/${id}`);
                    if (!response.ok) throw new Error("Failed to get task");

                    const task = await response.json();
                    setForm({
                        name: task.name,
                        description: task.description || "",
                        tags: task.tags || [],
                        priority: task.priority || undefined,
                    });
                } catch (err) {
                    if (err instanceof Error) setMessage(err.message);
                }
            };
            fetchTask();
        }
    }, [id]);

    const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
        const { name, value } = e.target;
        setForm(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            const response = await fetch(
                isEditMode
                ? `/api/v1/tasks/update/${id}`
                    : "/api/v1/tasks/new",
                {
                    method: isEditMode ? "PATCH" : "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(form),
                }
            );

            if (!response.ok) {
                throw new Error(`Failed to ${isEditMode ? "update" : "create"} task.`);
            }

            setMessage(`Task ${isEditMode ? "updated" : "created"} successfully.`);
            navigate("/");
        } catch (err) {
            if (err instanceof Error) {
                setMessage(err.message)
            }
        }

        /*
        const tags = form.tagsInput
            ? form.tagsInput.split(",").map(tag => tag.trim()).filter(tag => tag)
            : [];
        */
    };

    return (
        <div style={{ maxWidth: "600px", margin: "0 auto", padding: "20px" }}>
            <h2>{isEditMode ? "Edit Task" : "Create New Task"}</h2>
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
                    name="tags"
                    placeholder="Tags (comma-separated)"
                    value={form.tags?.join(", ") || ""}
                    onChange={(e) =>
                        setForm((prev) => ({
                            ...prev,
                            tags: e.target.value.split(",").map((tag) => tag.trim()),
                        }))
                    }
                />
                <select
                    name="priority"
                    value={form.priority || ""}
                    onChange={handleChange}
                >
                    <option value="">Select priority</option>
                    <option value="LOW">Low</option>
                    <option value="MEDIUM">Medium</option>
                    <option value="HIGH">High</option>
                </select>

                <button type="submit">{isEditMode ? "Update Task" : "Create Task"}</button>
            </form>
            {message && <p style={{ marginTop: "10px", color: "green" }}>{message}</p>}
        </div>
    );
};

export default TaskForm;
