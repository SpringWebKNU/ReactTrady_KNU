import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';  // Replace Switch with Routes
import QnaForm from './components/QnaForm';  // QnaForm 컴포넌트
import QnaList from './components/QnaList';  // Import QnaList component
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
    return (
        <Router>
            <Routes>
                {/* Update Route component with element */}
                <Route path="/qna/create" element={<QnaForm />} />
                <Route path="/qnas" element={<QnaList />} />
                {/* Add other routes here */}
            </Routes>
        </Router>
    );
}

export default App;
