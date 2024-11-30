import React, { useState } from 'react';

function QnAApp() {
  const [question, setQuestion] = useState('');
  const [questionsList, setQuestionsList] = useState([]);
  const [answers, setAnswers] = useState({});
  const [showComments, setShowComments] = useState({});

  const handleInputChange = (event) => {
    setQuestion(event.target.value);
  };

  const handleAnswerChange = (event, questionIndex) => {
    const newAnswers = { ...answers, [questionIndex]: event.target.value };
    setAnswers(newAnswers);
  };

  const handleSubmit = () => {
    if (question.trim()) {
      setQuestionsList([...questionsList, { text: question, comments: [] }]);
      setQuestion(''); // 입력 필드를 초기화
    }
  };

  const handleCommentSubmit = (index) => {
    if (answers[index]?.trim()) {
      const newQuestionsList = [...questionsList];
      newQuestionsList[index].comments.push(answers[index]);
      setQuestionsList(newQuestionsList);
      const newAnswers = { ...answers, [index]: '' }; // 답변 입력 필드 초기화
      setAnswers(newAnswers);
    }
  };

  const toggleComments = (index) => {
    setShowComments({ ...showComments, [index]: !showComments[index] });
  };

  return (
    <div style={{ padding: '20px', maxWidth: '800px', margin: 'auto', backgroundColor: '#f9f9f9', borderRadius: '8px', boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)' }}>
      <h1 style={{ textAlign: 'center', color: '#333' }}>QnA 화면</h1>
      <div style={{ marginBottom: '20px' }}>
        <input
          type="text"
          value={question}
          onChange={handleInputChange}
          placeholder="질문을 입력하세요..."
          style={{ width: '100%', padding: '10px', fontSize: '16px', borderRadius: '4px', border: '1px solid #ccc', marginBottom: '10px' }}
        />
        <button
          onClick={handleSubmit}
          style={{ display: 'block', width: '100%', padding: '10px', fontSize: '16px', backgroundColor: '#007bff', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
        >
          제출
        </button>
      </div>
      <div>
        <h2 style={{ color: '#333' }}>질문 목록</h2>
        <ul style={{ listStyleType: 'none', padding: 0 }}>
          {questionsList.map((q, index) => (
            <li key={index} style={{ backgroundColor: '#fff', borderRadius: '8px', padding: '15px', marginBottom: '10px', boxShadow: '0 2px 5px rgba(0, 0, 0, 0.1)' }}>
              <div style={{ fontSize: '18px', marginBottom: '10px' }}>{q.text}</div>
              <div style={{ marginBottom: '10px' }}>
                <input
                  type="text"
                  value={answers[index] || ''}
                  onChange={(e) => handleAnswerChange(e, index)}
                  placeholder="댓글을 입력하세요..."
                  style={{ width: '70%', padding: '8px', fontSize: '14px', borderRadius: '4px', border: '1px solid #ccc', marginRight: '5px' }}
                />
                <button
                  onClick={() => handleCommentSubmit(index)}
                  style={{ padding: '8px 12px', fontSize: '14px', backgroundColor: '#28a745', color: '#fff', border: 'none', borderRadius: '4px', cursor: 'pointer' }}
                >
                  댓글 쓰기
                </button>
                <button
                  onClick={() => toggleComments(index)}
                  style={{ padding: '8px 12px', fontSize: '14px', backgroundColor: '#007bff', color: '#fff', border: 'none', borderRadius: '4px', marginLeft: '5px', cursor: 'pointer' }}
                >
                  {showComments[index] ? '댓글 숨기기' : '댓글 보기'}
                </button>
              </div>
              {showComments[index] && q.comments.length > 0 && (
                <div style={{ marginTop: '10px' }}>
                  <h4 style={{ color: '#333' }}>댓글:</h4>
                  <ul style={{ listStyleType: 'none', padding: 0 }}>
                    {q.comments.map((comment, commentIndex) => (
                      <li key={commentIndex} style={{ borderBottom: '1px solid #ddd', padding: '5px 0', fontSize: '14px', color: '#555' }}>
                        {comment}
                      </li>
                    ))}
                  </ul>
                </div>
              )}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}

export default QnAApp;
