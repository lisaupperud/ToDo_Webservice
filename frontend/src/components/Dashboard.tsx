import styles from "./Dashboard.module.css"
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import * as React from "react";

interface TaskDTO {
    id: string;
    name: string;
    description?: string;
    tags?: string[];
    priority?: string;
    completed: boolean;
}

const Dashboard: React.FC = () => {
    const [tasks, setTasks] = useState<TaskDTO[]>([]);
    const [loading, setLoading] = useState<boolean>(false);
    const [error, setError] = useState<string | null>(null);
    const [searchValue, setSearchValue] = useState<string>("");
    const [searchType, setSearchType] = useState<"name" | "tag">("name");
    const [filter, setFilter] = useState<"uncompleted" | "all" | "completed" | "priority" | "no-priority">("uncompleted");
    const navigate = useNavigate();

    const fetchTasks = async (url: string) => {
        setLoading(true);

        try {
            const response = await fetch(url);
            if (!response.ok && response.status !== 204) {
                throw new Error(`Error fetching tasks: ${response.statusText}`);
            }
            const data: TaskDTO[] = await response.json();
            setTasks(data);
        } catch (err) {
            if (err instanceof Error) {
                setError(err.message);
            }
        } finally {
            setLoading(false);
        }
    };
    useEffect(() => {
        let endpoint = "/api/v1/tasks/";
        switch (filter) {
            case "all":
                endpoint = "/api/v1/tasks/all"
                break;
            case "priority":
                endpoint = "/api/v1/tasks/sort/no-priority"
                break;
            default:
                endpoint = "/api/v1/tasks/"
        }
        fetchTasks(endpoint);
    }, [filter]);

    const handleSearch = async () => {
        if (!searchValue.trim()) return;

        let endpoint = searchType === "name"
            ? `/api/v1/tasks/search/name/${encodeURIComponent(searchValue)}`
            : `/api/v1/tasks/tag/${encodeURIComponent(searchValue)}`;

        try {
            const response = await fetch(endpoint);
            if (!response.ok) {
                throw new Error("Search failed")
            }

            const data = await response.json();
            if (data && Array.isArray(data) && data.length > 0) {
                setTasks(data);
                setError(null)
            } else {
                throw new Error("No tasks matching your search.")
            }
        } catch (err) {
            if (err instanceof Error) {
                setError(err.message);
            }
        }
    };

    const handleFilterChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
        setFilter(e.target.value as typeof filter);
    }

    const completeTask = async (id: string) => {
        try {
            await fetch(`/api/v1/tasks/complete/${id}`, {
                method: "PATCH",
            });
            fetchTasks("/api/v1/tasks/");
        } catch (err) {
            console.error(err);
        }
    };

    const moveToTrash = async (id: string) => {
        try {
            await fetch(`/api/v1/tasks/trash/${id}`, {
                method: "PUT",
            });
            fetchTasks("/api/v1/tasks/");
        } catch (err) {
            console.error(err);
        }
    };

    const handleEdit = (id: string) => {
        navigate(`/edit/${id}`);
    };

    const navigateToRegisterTask = () => {
        navigate("/new");
    };

    const handleMoveCompletedToTrash = async () => {
        try {
            const response = await fetch("/api/v1/tasks/trash/completed", {
                method: "PUT"
            });

            if (!response.ok) {
                throw new Error("Failed to move completed tasks to trash.");
            }

            const message = await response.text();
            alert(message);
            fetchTasks("/api/v1/tasks/"); // Refresh uncompleted tasks
        } catch (err) {
            if (err instanceof Error) {
                setError(err.message);
            }
        }
    };

    if (loading) return <p>Loading tasks...</p>;
    if (error) return <p style={{color: "red"}}>Error: {error}</p>;
    if (tasks.length === 0) return <p>No uncompleted tasks found.</p>;

    return (
        <div style={{margin: "0 auto", padding: "20px"}}>
            <h1>Uncompleted Tasks</h1>
            <button onClick={navigateToRegisterTask}>+ Register New Task</button>
            <button onClick={handleMoveCompletedToTrash}>Move Completed to Trash</button>


            {error && <p style={{color: "red"}}>{error}</p>}

            {/* Search */}
            <div style={{marginTop: "20px"}}>
                <label>
                    <input
                        type="radio"
                        value="name"
                        checked={searchType === "name"}
                        onChange={() => setSearchType("name")}
                    /> Search by Name
                </label>
                <label style={{marginLeft: "20px"}}>
                    <input
                        type="radio"
                        value="tag"
                        checked={searchType === "tag"}
                        onChange={() => setSearchType("tag")}
                    /> Search by Tag
                </label>
                <div>
                    <input
                        type="text"
                        placeholder={`Enter ${searchType}`}
                        value={searchValue}
                        onChange={(e) => setSearchValue(e.target.value)}
                    />
                    <button onClick={handleSearch}>Search</button>
                </div>
            </div>

            {/* Filter */}
            <div style={{marginTop: "20px"}}>
                <label>Filter tasks: </label>
                <select value={filter} onChange={handleFilterChange}>
                    <option value="uncompleted">Uncompleted</option>
                    <option value="all">All</option>
                    <option value="completed">Completed (client filtered)</option>
                    <option value="priority">Sorted by Priority</option>
                    <option value="no-priority">No Priority</option>
                </select>
            </div>

            {/* Tasks */}
            <div className={styles.taskList}>
                {loading && <p>Loading tasks...</p>}
                {!loading && tasks.length === 0 && <p>No tasks found.</p>}
                <div className={styles.posts}>
                    {tasks
                        .filter((task) => filter === "completed" ? task.completed : true)
                        .map((task) => (
                            <div
                                key={task.id}
                                className={styles.taskCard}
                            >
                                <p><strong>Name:</strong> {task.name}</p>
                                {task.description && (
                                    <p><strong>Description:</strong> {task.description}</p>
                                )}
                                {task.tags && task.tags.length > 0 && (
                                    <p><strong>Tags:</strong> {task.tags.join(", ")}</p>
                                )}
                                {task.priority && (
                                    <p><strong>Priority:</strong> {task.priority}</p>
                                )}
                                <p><strong>Completed:</strong> {task.completed ? "Yes" : "No"}</p>
                                <div style={{marginTop: "10px", display: "flex", gap: "10px"}}>
                                    <button onClick={() => completeTask(task.id)}>✅ Complete</button>
                                    <button onClick={() => moveToTrash(task.id)}>🗑️ Trash</button>
                                    <button onClick={() => handleEdit(task.id)}>✏️ Edit</button>
                                </div>
                            </div>
                        ))}
                </div>
            </div>
        </div>
    );
};

export default Dashboard;
