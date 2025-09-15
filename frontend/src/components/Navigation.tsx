import {Link} from "react-router-dom";

const Navigation = () => {
    return (
        <nav style={{ padding: "1rem", backgroundColor: "#f0f0f0" }}>
            <Link to="/" style={{ marginRight: "10px" }}>Home</Link>
            <Link to="/new" style={{ marginRight: "10px" }}>Register Task</Link>
            <Link to="/stats">Task Stats</Link>
            <Link to="/trashcan">Trashcan</Link>
        </nav>
    );
};

export default Navigation;