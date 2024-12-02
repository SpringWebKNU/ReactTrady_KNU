import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const QnaList = () => {
    const [qnas, setQnas] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        // GET 요청으로 Q&A 데이터를 가져옵니다
        axios.get('http://localhost:8080/api/qnas')
            .then(async (response) => {
                const qnasWithAnswerCount = await Promise.all(response.data.map(async (qna) => {
                    try {
                        // 각 Q&A의 답변 개수 가져온다.
                        const answerResponse = await axios.get(`http://localhost:8080/api/qnas/${qna.id}/answers`);
                        return { ...qna, answerCount: answerResponse.data.length };
                    } catch (error) {
                        console.error(`Error fetching answers for Qna ${qna.id}:`, error);
                        return { ...qna, answerCount: 0 };
                    }
                }));
                setQnas(qnasWithAnswerCount);
            })
            .catch(error => console.error('Error fetching Qnas:', error));
    }, []);

    const handleCreateQna = () => {
        navigate('/qna/create');
    };

    const handleViewQna = (qnaId) => {
        navigate(`/qna/${qnaId}`);
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

                <br />
                <div className="table-responsive">
                    <table className="table table-striped table-bordered">
                        <thead>
                        <tr>
                            <th scope="col">번호</th>
                            <th scope="col">제목</th>
                            <th scope="col">작성일</th>
                            <th scope="col">답변 개수</th>
                        </tr>
                        </thead>
                        <tbody>
                        {qnas.map((qna, index) => (
                            <tr key={qna.id} onClick={() => handleViewQna(qna.id)}>
                                <td>{index + 1}</td>
                                <td>{qna.title}</td>
                                <td>{new Date(qna.createdAt).toLocaleDateString()} {new Date(qna.createdAt).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</td>
                                <td>{qna.answerCount}</td>
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
