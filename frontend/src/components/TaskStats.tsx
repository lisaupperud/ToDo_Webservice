import styles from "./TaskStats.module.css";
import {useEffect, useState} from "react";

type TagStats = {
    tag: string;
    count: number;
};

const TaskStats = () => {
    const [tags, setTags] = useState<TagStats[]>([]);
    const [message, setMessage] = useState("");

    useEffect(() => {
        const fetchTagStats = async () => {
            try {
                const response = await fetch("/api/v1/tasks/tag/stats", {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                });

                if (response.status === 204) {
                    setMessage("No tag statistics available.");
                    return;
                }

                if (!response.ok) {
                    throw new Error("Failed to fetch tag stats.");
                }

                const data = await response.json();
                setTags(data);
            } catch (err) {
                if (err instanceof Error) {
                    setMessage(err.message);
                }
            }
        };
        fetchTagStats();
    }, []);

    return (
        <div className={styles.biggestContainer}>
            <h2>Task Tag Statistics</h2>
            {message && <p>{message}</p>}

            {tags.length > 0 && (
                <ul className={styles.statContainer}>
                    {tags.map((tagStat, index) => (
                        <li key={index}>
                            {tagStat.tag} — used {tagStat.count} times
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default TaskStats;
