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
    const [isAdmin, setIsAdmin] = useState(false);
    const [showLogin, setShowLogin] = useState(false);
    const [loginError, setLoginError] = useState('');
    const [rememberMe, setRememberMe] = useState(false);

    useEffect(() => {
        axios.get(`http://localhost:8080/api/qnas/${qnaId}`)
            .then(response => {
                setQna(response.data);
            })
            .catch(error => console.error('Error fetching Qna detail:', error));

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
            const response = await axios.delete(`http://localhost:8080/api/qnas/${qnaId}/answers/${answerId}`);

            if (response.status === 204) {
                setAnswers(prevAnswers => prevAnswers.filter(answer => answer.id !== answerId));
            }
        } catch (error) {
            console.error('Error deleting answer:', error);
            alert('답변 삭제에 실패했습니다.');
        }
    };

    const handleDeleteQna = async () => {
        try {
            for (const answer of answers) {
                await axios.delete(`http://localhost:8080/api/qnas/${qnaId}/answers/${answer.id}`);
            }

            const response = await axios.delete(`http://localhost:8080/api/qnas/${qnaId}`);

            if (response.status === 204) {
                alert('게시글이 성공적으로 삭제되었습니다.');
                navigate('/qnas');
            }
        } catch (error) {
            console.error('Error deleting Qna:', error);
            alert('게시글 삭제에 실패했습니다.');
        }
    };

    const handleAdminLogin = (e) => {
        e.preventDefault();

        const { username, password } = e.target.elements;
        if (username.value === 'admin' && password.value === 'admin') {
            setIsAdmin(true);
            setShowLogin(false);
            setLoginError('');
        } else {
            setLoginError('아이디 또는 비밀번호가 잘못되었습니다.');
            if (rememberMe) {
                e.target.elements.password.value = '';
            } else {
                e.target.elements.username.value = '';
                e.target.elements.password.value = '';
            }
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

            <button className="btn btn-outline-primary mb-3" style={{ borderRadius: '20px', backgroundColor: '#007bff', color: 'white' }} onClick={() => setShowLogin(!showLogin)}>
                관리자 로그인 하기
            </button>

            {showLogin && (
                <form onSubmit={handleAdminLogin} className="mb-3">
                    <div className="form-group">
                        <input type="text" name="username" className="form-control mb-2" placeholder="아이디" style={{ width: '200px' }} required />
                        <input type="password" name="password" className="form-control mb-2" placeholder="비밀번호" style={{ width: '200px' }} required />
                        <div className="form-check">
                            <input
                                type="checkbox"
                                className="form-check-input"
                                id="rememberMe"
                                checked={rememberMe}
                                onChange={() => setRememberMe(!rememberMe)}
                            />
                            <label className="form-check-label" htmlFor="rememberMe">
                                아이디 저장
                            </label>
                        </div>
                    </div>
                    {loginError && <div className="alert alert-danger">{loginError}</div>}
                    <button type="submit" className="btn btn-primary">로그인</button>
                </form>
            )}

            {qna ? (
                <>
                    <div className="mb-4 card">
                        <div className="card-body">
                            <h5 className="card-title">{qna.title}</h5>
                            <p className="card-text">{qna.content}</p>
                            <footer className="blockquote-footer">
                                작성일: {new Date(qna.createdAt).toLocaleDateString()}
                            </footer>
                        </div>
                    </div>

                    {isAdmin && (
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
                    )}

                    <div className="mt-4">
                        <h4>답변 목록</h4>
                        {answers.length > 0 ? (
                            <div className="list-group">
                                {answers.map((answer) => (
                                    <div key={answer.id} className="list-group-item d-flex justify-content-between align-items-center">
                                        <p className="mb-1">{answer.content}</p>
                                        {isAdmin && (
                                            <button
                                                onClick={() => handleDeleteAnswer(answer.id)}
                                                className="btn btn-danger btn-sm ml-2">
                                                삭제
                                            </button>
                                        )}
                                    </div>
                                ))}
                            </div>
                        ) : (
                            <p className="text-muted">답변이 아직 없습니다.</p>
                        )}
                    </div>

                    {isAdmin && (
                        <div className="mt-4 text-center">
                            <button
                                className="btn btn-danger"
                                onClick={handleDeleteQna}>
                                게시글 삭제
                            </button>
                        </div>
                    )}
                </>
            ) : (
                <p>Q&A 정보를 불러오는 중...</p>
            )}
        </div>
    );
};

export default QnaDetail;
