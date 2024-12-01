import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import QnaForm from "./components/QnaForm";
import QnaList from "./components/QnaList";
import QnaDetail from "./components/QnaDetail";
import "bootstrap/dist/css/bootstrap.min.css";

function App() {
    const [loggedInUser, setLoggedInUser] = useState(null); // Stores the logged-in user
    const [isAdmin, setIsAdmin] = useState(false); // Indicates whether the logged-in user is an admin

    const msg = "Q&A입니다. 문의사항을 남겨주세요."; // Custom message

    return (
        <Router>
            <Header loggedInUser={loggedInUser} isAdmin={isAdmin} msg={msg} />
            <main className="container my-4">
                <Routes>
                    <Route path="/qna/create" element={<QnaForm />} />
                    <Route path="/qnas" element={<QnaList loggedInUser={loggedInUser} isAdmin={isAdmin} />} />
                    <Route path="/qna/:qnaId" element={<QnaDetail loggedInUser={loggedInUser} isAdmin={isAdmin} />} />
                </Routes>
            </main>
            <Footer />
        </Router>
    );
}

export default App;
