import styles from "./Navigation.module.css";
import {Link} from "react-router-dom";

const Navigation = () => {
    return (
        <nav className={styles.navbar}>
            <Link to="/">Home</Link>
            <Link to="/new">Register Task</Link>
            <Link to="/stats">Task Stats</Link>
            <Link to="/trashcan">Trashcan</Link>
        </nav>
    );
};

export default Navigation;