import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const QnaList = () => {
    const [qnas, setQnas] = useState([]);
    const navigate = useNavigate();  // 페이지 이동을 위한 hook

    useEffect(() => {
        // GET 요청으로 Q&A 데이터를 가져옵니다
        axios.get('http://localhost:8080/api/qnas')
            .then(response => setQnas(response.data))
            .catch(error => console.error('Error fetching Qnas:', error));
    }, []);

    const handleCreateQna = () => {
        // Q&A 작성 페이지로 이동
        navigate('/qna/create');
    };

    return (
        <div className="container mt-5">
            <h1 className="mb-4 text-center">Trandy Q&A</h1>

            <div className="mb-4">
            <div className="d-flex justify-content-between">
                <div className="ms-auto">
                    <button className="btn btn-primary" onClick={handleCreateQna}>Q&A 작성하기</button>
                </div>
            </div>

                <br></br>
                <div className="table-responsive">
                    <table className="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">번호</th>
                                <th scope="col">제목</th>
                                <th scope="col">내용</th>
                                <th scope="col">작성자</th>
                                <th scope="col">작성일</th>
                            </tr>
                        </thead>
                        <tbody>
                            {qnas.map((qna, index) => (
                                <tr key={qna.id}>
                                    <td>{index + 1}</td>
                                    <td>{qna.title}</td>
                                    <td>{qna.content.length > 50 ? qna.content.slice(0, 50) + '...' : qna.content}</td>
                                    <td>{qna.member.id}</td>
                                    <td>{new Date(qna.createdAt).toLocaleDateString()}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    );
};

export default QnaList;
