import styles from "./Dashboard.module.css";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";

interface DeletedTaskDTO {
    id: string;
    name: string;
    description?: string;
    tags?: string[];
    priority?: string;
    completed: boolean;
}

const Trashcan: React.FC = () => {
    const [deletedTasks, setDeletedTasks] = useState<DeletedTaskDTO[]>([]);
    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const navigate = useNavigate();

    const fetchTrash = async () => {
        setLoading(true);
        try {
            const response = await fetch("/api/v1/trashcan/");
            if (response.status === 204) {
                setDeletedTasks([]);
            } else if (!response.ok) {
                throw new Error("Failed to fetch trashcan.");
            } else {
                const data = await response.json();
                setDeletedTasks(data);
            }
        } catch (err) {
            if (err instanceof Error) {
                setError(err.message);
            }
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchTrash();
    }, []);

    const handleRestore = async (id: string) => {
        try {
            const response = await fetch(`/api/v1/tasks/restore/${id}`, {
                method: "PUT",
            });

            if (!response.ok) {
                throw new Error("Failed to restore task.");
            }

            fetchTrash(); // Refresh trash list
        } catch (err) {
            if (err instanceof Error) {
                setError(err.message);
            }
        }
    };

    const handleDelete = async (id: string) => {
        try {
            const response = await fetch(`/api/v1/trashcan/delete/${id}`, {
                method: "DELETE",
            });

            if (!response.ok && response.status !== 204) {
                throw new Error("Failed to delete task from trash.");
            }

            fetchTrash(); // Refresh trash list
        } catch (err) {
            if (err instanceof Error) {
                setError(err.message);
            }
        }
    };

    const handleBack = () => {
        navigate("/");
    };

    return (
        <div style={{margin: "0 auto", padding: "20px"}}>
            <h1>🧺 Trashcan</h1>
            <button onClick={handleBack}>← Back to Dashboard</button>

            <div className={styles.taskList}>
                {error && <p style={{color: "red"}}>{error}</p>}
                {loading ? (
                    <p>Loading deleted tasks...</p>
                ) : deletedTasks.length === 0 ? (
                    <p>No deleted tasks.</p>
                ) : (
                    <div className={styles.posts}>
                        {deletedTasks.map(task => (
                            <div
                                key={task.id}
                                className={styles.taskCard}
                            >
                                <p><strong>Name:</strong> {task.name}</p>
                                {task.description && <p><strong>Description:</strong> {task.description}</p>}
                                {task.tags && task.tags.length > 0 && (
                                    <p><strong>Tags:</strong> {task.tags.join(", ")}</p>
                                )}
                                {task.priority && <p><strong>Priority:</strong> {task.priority}</p>}
                                <p><strong>Completed:</strong> {task.completed ? "Yes" : "No"}</p>
                                <div style={{marginTop: "10px", display: "flex", gap: "10px"}}>
                                    <button onClick={() => handleRestore(task.id)}>♻️ Restore</button>
                                    <button onClick={() => handleDelete(task.id)}>❌ Permanently Delete</button>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>


        </div>
    );
};

export default Trashcan;
