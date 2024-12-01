import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

const QnaDetail = () => {
    const [qna, setQna] = useState(null);
    const [answer, setAnswer] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const { qnaId } = useParams();
    const navigate = useNavigate();

    const [answers, setAnswers] = useState([]);

    // 페이지 로드 시 Q&A 데이터와 답변 목록을 서버에서 가져옵니다.
    useEffect(() => {
        axios.get(`http://localhost:8080/api/qnas/${qnaId}`)
            .then(response => {
                setQna(response.data);
            })
            .catch(error => console.error('Error fetching Qna detail:', error));

        // 해당 Q&A에 대한 모든 답변을 서버에서 불러옵니다.
        loadAnswers();
    }, [qnaId]);

    const loadAnswers = () => {
        axios.get(`http://localhost:8080/api/qnas/${qnaId}/answers`)
            .then(response => {
                setAnswers(response.data);
            })
            .catch(error => {
                console.error('Error fetching answers:', error);
            });
    };

    const handleAnswerSubmit = async (e) => {
        e.preventDefault();

        if (!answer) {
            setErrorMessage('답변을 입력해주세요.');
            return;
        }

        try {
            const response = await axios.post(`http://localhost:8080/api/qnas/${qnaId}/answers`, { content: answer });

            if (response.status === 200) {
                // 서버에서 새로 추가된 답변을 가져오기
                loadAnswers();

                setAnswer('');
                setErrorMessage('');
                alert('답변이 성공적으로 작성되었습니다.');
            }
        } catch (error) {
            setErrorMessage('답변 작성에 실패했습니다. 다시 시도해 주세요.');
        }
    };

    const handleBackToList = () => {
        navigate('/qnas');
    };

    const handleDeleteAnswer = async (answerId) => {
        try {
            // DELETE 요청으로 답변 삭제
            const response = await axios.delete(`http://localhost:8080/api/qnas/${qnaId}/answers/${answerId}`);

            if (response.status === 204) {
                // 삭제된 후 답변 목록을 갱신
                setAnswers(prevAnswers => prevAnswers.filter(answer => answer.id !== answerId));
            }
        } catch (error) {
            console.error('Error deleting answer:', error);
            alert('답변 삭제에 실패했습니다.');
        }
    };

    const handleDeleteQna = async () => {
        try {
            // 게시글에 연결된 모든 답변을 먼저 삭제합니다.
            for (const answer of answers) {
                await axios.delete(`http://localhost:8080/api/qnas/${qnaId}/answers/${answer.id}`);
            }

            // 모든 답변을 삭제한 후 게시글을 삭제합니다.
            const response = await axios.delete(`http://localhost:8080/api/qnas/${qnaId}`);

            if (response.status === 204) {
                alert('게시글이 성공적으로 삭제되었습니다.');
                navigate('/qnas');  // 게시글 삭제 후 Q&A 목록 페이지로 이동
            }
        } catch (error) {
            console.error('Error deleting Qna:', error);
            alert('게시글 삭제에 실패했습니다.');
        }
    };

    return (
        <div className="container mt-4">
            <div className="d-flex justify-content-between mb-4">
                <h2>{qna ? qna.title : 'Q&A 상세'}</h2>
                <button className="btn btn-secondary" onClick={handleBackToList}>
                    목록으로 가기
                </button>
            </div>
            {qna ? (
                <>
                    <div className="mb-4 card">
                        <div className="card-body">
                            <h5 className="card-title">{qna.title}</h5>
                            <p className="card-text">{qna.content}</p>
                            <footer className="blockquote-footer">
                                작성자: {qna.member?.id || '작성자 정보 없음'} |
                                작성일: {new Date(qna.createdAt).toLocaleDateString()}
                            </footer>
                        </div>
                    </div>

                    <div className="mt-4">
                        <h4>답변 작성</h4>
                        {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}
                        <form onSubmit={handleAnswerSubmit}>
                            <div className="form-group">
                                <textarea
                                    className="form-control"
                                    value={answer}
                                    onChange={(e) => setAnswer(e.target.value)}
                                    rows="4"
                                    required
                                />
                            </div>
                            <button type="submit" className="btn btn-primary mt-2">답변 작성</button>
                        </form>
                    </div>

                    <div className="mt-4">
                        <h4>답변 목록</h4>
                        <div className="list-group">
                            {answers.map((answer) => (
                                <div key={answer.id} className="list-group-item d-flex justify-content-between align-items-center">
                                    <p className="mb-1">{answer.content}</p>
                                    <small>작성자: {answer.member?.id || '알 수 없음'}</small>
                                    <button
                                        onClick={() => handleDeleteAnswer(answer.id)}
                                        className="btn btn-danger btn-sm ml-2">
                                        삭제
                                    </button>
                                </div>
                            ))}
                        </div>
                    </div>

                    <div className="mt-4 text-center">
                        <button
                            className="btn btn-danger"
                            onClick={handleDeleteQna}>
                            게시글 삭제
                        </button>
                    </div>

                </>
            ) : (
                <p>Q&A 정보를 불러오는 중...</p>
            )}
        </div>
    );
};

export default QnaDetail;
