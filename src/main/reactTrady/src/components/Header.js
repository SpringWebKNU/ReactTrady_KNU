import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";

const Header = ({ loggedInUser, isAdmin, msg }) => {
    const [user, setUser] = useState(null);
    const [admin, setAdmin] = useState(false);

    useEffect(() => {
        fetch('/api/session') // Endpoint that returns session info (e.g., loggedInUser and isAdmin)
            .then(response => response.json())
            .then(data => {
                setUser(data.currentUser);
                setAdmin(data.isAdmin);
            });
    }, []);

    return (
        <>
            <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
                <div className="container-fluid">
                    <Link className="navbar-brand" to="http://localhost:8080/products/all" style={{ fontSize: "1.8rem", fontWeight: "bold", color: "#fff" }}>
                        Trady
                    </Link>
                    <button
                        className="navbar-toggler"
                        type="button"
                        data-bs-toggle="collapse"
                        data-bs-target="#navbarNav"
                        aria-controls="navbarNav"
                        aria-expanded="false"
                        aria-label="Toggle navigation"
                    >
                        <span className="navbar-toggler-icon"></span>
                    </button>
                    <div className="collapse navbar-collapse" id="navbarNav">
                        <ul className="navbar-nav me-auto">
                            <li className="nav-item">
                                <Link className="nav-link" to="http://localhost:8080/products/all">메인</Link>
                            </li>
                            <li className="nav-item">
                                <Link className="nav-link" to="http://localhost:8080/products">카테고리</Link>
                            </li>
                            <li className="nav-item">
                                <a className="nav-link" href="http://localhost:3000/qnas">Q&A</a>
                            </li>
                        </ul>

                        <form action="http://localhost:8080/products/search" method="get" className="d-flex">
                            <input className="form-control me-2" type="text" id="search" name="keyword" placeholder="Search" />
                            <button className="btn btn-outline-light" type="submit">Search</button>
                        </form>

                        <ul className="navbar-nav">
                            {user ? (
                                <>
                                    <li className="nav-item">
                                        <form action="/members/logout" method="POST" className="d-inline">
                                            <button type="submit" className="nav-link btn btn-link text-light">로그아웃</button>
                                        </form>
                                    </li>
                                    {!isAdmin && (
                                        <li className="nav-item">
                                            <Link className="nav-link" to="http://localhost:8080/members/mypage">마이페이지</Link>
                                        </li>
                                    )}
                                    {isAdmin && (
                                        <li className="nav-item">
                                            <Link className="nav-link" to="http://localhost:8080/members/admin">관리자 모드</Link>
                                        </li>
                                    )}
                                </>
                            ) : (
                                <>
                                    <li className="nav-item">
                                        <Link className="nav-link" to="http://localhost:8080/members/login">로그인</Link>
                                    </li>
                                    <li className="nav-item">
                                        <Link className="nav-link" to="http://localhost:8080/members/join">회원가입</Link>
                                    </li>
                                </>
                            )}
                        </ul>
                    </div>
                </div>
            </nav>

            {msg && (
                <div className="alert alert-primary alert-dismissible">
                    {msg}
                    <button type="button" className="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            )}
        </>
    );
};

export default Header;
