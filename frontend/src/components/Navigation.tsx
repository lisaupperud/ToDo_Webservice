import {Link} from "react-router-dom";

const Navigation = () => {
    return (
        <nav style={{ padding: "1rem", backgroundColor: "#f0f0f0" }}>
            <Link to="/" style={{ marginRight: "20px" }}>Home</Link>
            <Link to="/new" style={{ marginRight: "20px" }}>Register Task</Link>
            <Link to="/stats" style={{ marginRight: "20px" }}>Task Stats</Link>
            <Link to="/trashcan" style={{ marginRight: "20px" }}>Trashcan</Link>
        </nav>
    );
};

export default Navigation;