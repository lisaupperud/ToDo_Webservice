import './App.css'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import Dashboard from "./components/Dashboard.tsx";
import Navigation from "./components/Navigation.tsx";
import TaskForm from './components/TaskForm';
import TaskStats from './components/TaskStats';
import Trashcan from "./components/Trashcan.tsx";

function App() {

    return (
        <>
            <BrowserRouter>
                <Navigation />
                <Routes>
                    <Route path="/" element={<Dashboard />} />
                    <Route path="/new" element={<TaskForm />} />
                    <Route path="/edit/:id" element={<TaskForm />} />
                    <Route path="/stats" element={<TaskStats />} />
                    <Route path="/trashcan" element={<Trashcan />} />
                </Routes>
            </BrowserRouter>
        </>
    )
}

export default App
