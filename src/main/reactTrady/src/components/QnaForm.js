import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const QnaForm = () => {
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const navigate = useNavigate();  // 페이지 이동을 위한 hook

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!title || !content) {
            setErrorMessage('제목과 내용을 모두 입력해주세요.');
            return;
        }

        try {
            // POST 요청으로 Q&A 작성하기
            const response = await axios.post('http://localhost:8080/api/qnas', {
                title: title,
                content: content,
                memberId: 1, // 로그인한 사용자의 ID로 변경해야 함
            });

            if (response.status === 200) {
                alert('Q&A가 성공적으로 작성되었습니다.');
                navigate('/qnas');  // 작성 후 Q&A 목록 페이지로 이동
            }
        } catch (error) {
            setErrorMessage('Q&A 작성에 실패했습니다. 다시 시도해 주세요.');
        }
    };

    return (
        <div className="container mt-4">
            <h2>Q&A 작성</h2>
            {errorMessage && <div className="alert alert-danger">{errorMessage}</div>}

            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label htmlFor="title" className="form-label">제목</label>
                    <input
                        type="text"
                        className="form-control"
                        id="title"
                        value={title}
                        onChange={(e) => setTitle(e.target.value)}
                        required
                    />
                </div>
                <div className="mb-3">
                    <label htmlFor="content" className="form-label">내용</label>
                    <textarea
                        className="form-control"
                        id="content"
                        rows="4"
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" className="btn btn-primary">Q&A 작성</button>
            </form>
        </div>
    );
};

export default QnaForm;
