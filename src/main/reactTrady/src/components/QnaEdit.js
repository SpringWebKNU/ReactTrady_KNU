import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

const QnaEdit = () => {
    const [qna, setQna] = useState(null);
    const [title, setTitle] = useState('');
    const [content, setContent] = useState('');
    const { qnaId } = useParams();
    const navigate = useNavigate();

    // 게시글 데이터 로드
    useEffect(() => {
        axios.get(`http://localhost:8080/api/qnas/${qnaId}`)
            .then(response => {
                setQna(response.data);
                setTitle(response.data.title);
                setContent(response.data.content);
            })
            .catch(error => {
                console.error('Error fetching Qna data:', error);
            });
    }, [qnaId]);

    // 수정된 게시글 저장
    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const updatedQna = {
                title,
                content
            };

            const response = await axios.patch(`http://localhost:8080/api/qnas/${qnaId}`, updatedQna);

            if (response.status === 200) {
                alert('게시글이 수정되었습니다.');
                navigate(`/qna/${qnaId}`);  // 수정 후 상세 페이지로 이동
            }
        } catch (error) {
            console.error('Error updating Qna:', error);
            alert('게시글 수정에 실패했습니다.');
        }
    };

    return (
        <div className="container mt-4">
            <h2>Q&A 수정</h2>
            {qna ? (
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label>제목</label>
                        <input
                            type="text"
                            className="form-control"
                            value={title}
                            onChange={(e) => setTitle(e.target.value)}
                            required
                        />
                    </div>
                    <div className="form-group mt-3">
                        <label>내용</label>
                        <textarea
                            className="form-control"
                            value={content}
                            onChange={(e) => setContent(e.target.value)}
                            rows="4"
                            required
                        />
                    </div>
                    <button type="submit" className="btn btn-primary mt-3">수정하기</button>
                </form>
            ) : (
                <p>Q&A 정보를 불러오는 중...</p>
            )}
        </div>
    );
};

export default QnaEdit;
