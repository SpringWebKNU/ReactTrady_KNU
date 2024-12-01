import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import QnaForm from "./components/QnaForm";
import QnaList from "./components/QnaList";
import QnaDetail  from "./components/QnaDetail";
import "bootstrap/dist/css/bootstrap.min.css";

function App() {
    const loggedInUser = true; // Replace with your actual logged-in state
    const isAdmin = false; // Replace with your actual admin state
    const msg = "Q&A입니다. 문의사항을 남겨주세요."; // Replace with your actual message

    return (
        <Router>
            <Header loggedInUser={loggedInUser} isAdmin={isAdmin} msg={msg} />
            <main className="container my-4">
                <Routes>
                    <Route path="/qna/create" element={<QnaForm />} />
                    <Route path="/qnas" element={<QnaList />} />
                    <Route path="/qna/:qnaId" element={<QnaDetail />} />
                    {/* Add other routes here */}
                </Routes>
            </main>
            <Footer />
        </Router>
    );
}

export default App;
