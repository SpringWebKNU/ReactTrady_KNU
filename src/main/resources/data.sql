INSERT INTO member(username, password, email, phonenumber, addr)
VALUES
    ('admin','admin','admin@admin.com', '010-0000-0000', 'admin'),
    ('dododo','123','peter200111@naver.com', '010-1234-5678', '서울특별시 종로구 사직로 161'),
    ('jungb1203','456','jungb1203@naver.com', '010-9876-5432', '서울 용산구 남산공원길 105'),
    ('gogogo','789','789@naver.com', '010-1928-3746', '전북 전주시 완산구 기린대로 99'),
    ('hohoho','012','012@naver.com', '010-5473-3245', '부산 해운대구 우동');


INSERT INTO pcategory (id, pname, is_first, is_selected)
VALUES
    (1, '나이키', TRUE, TRUE),
    (2, '아디다스', FALSE, FALSE),
    (3, '아식스', FALSE, FALSE),
    (4, '뉴발란스', FALSE, FALSE),
    (5, '기타', FALSE, FALSE);

-- Product 테이블에 데이터 삽입 (가격 제외) - 상품명 수정
INSERT INTO product (pname, pimg, pcategory_id, pdate)
VALUES
    ('Converse Chuck Taylor All Star Low Top', 'shoes1.jpg', 5, '2023-01-01'),
    ('Asics Gel-Kayano 28', 'shoes2.jpg', 3, '2023-02-01'),
    ('New Balance 990v5', 'shoes3.jpg', 4, '2023-03-01'),
    ('UGG Classic Short II', 'shoes4.jpg', 5, '2023-04-01'),
    ('Adidas Ultraboost 22', 'shoes5.jpg', 2, '2023-05-01'),
    ('Adidas NMD_R1', 'adidas1.jpg', 2, '2023-05-01'),
    ('Adidas Stan Smith', 'adidas2.jpg', 2, '2023-05-01'),
    ('Adidas Superstar', 'adidas3.jpg', 2, '2023-05-01'),
    ('Adidas Gazelle', 'adidas4.jpg', 2, '2023-05-01'),
    ('Adidas ZX 2K Boost', 'adidas5.jpg', 2, '2023-05-01'),
    ('Asics Gel-Nimbus 23', 'asic1.jpg', 3, '2023-05-01'),
    ('Asics Gel-Kayano 27', 'asic2.jpg', 3, '2023-05-01'),
    ('Asics GT-2000 10', 'asic3.jpg', 3, '2023-05-01'),
    ('Asics Gel-Cumulus 23', 'asic4.jpg', 3, '2023-05-01'),
    ('Asics Gel-Pulse 13', 'asic5.jpg', 3, '2023-05-01'),
    ('Converse Chuck Taylor All Star High Top', 'Etc1.jpg', 5, '2023-05-01'),
    ('Skechers GOwalk 5', 'Etc2.jpg', 5, '2023-05-01'),
    ('Puma Suede Classic', 'Etc3.jpg', 5, '2023-05-01'),
    ('MLB Classic Leather', 'Etc4.jpg', 5, '2023-05-01'),
    ('Fila Disruptor II', 'Etc5.jpg', 5, '2023-05-01'),
    ('New Balance 574', 'Newbal1.jpg', 4, '2023-05-01'),
    ('New Balance 997H', 'Newbal2.jpg', 4, '2023-05-01'),
    ('New Balance 1080v11', 'Newbal3.jpg', 4, '2023-05-01'),
    ('New Balance 990v4', 'Newbal4.jpg', 4, '2023-05-01'),
    ('New Balance Fresh Foam 880v10', 'Newbal5.jpg', 4, '2023-05-01'),
    ('Nike Air Max 90', 'nike1.jpg', 1, '2023-05-01'),
    ('Nike Air Force 1', 'nike2.jpg', 1, '2023-05-01'),
    ('Nike ZoomX Vaporfly Next%', 'nike3.jpg', 1, '2023-05-01'),
    ('Nike React Infinity Run Flyknit', 'nike4.jpg', 1, '2023-05-01'),
    ('Nike Air Zoom Pegasus 38', 'nike5.jpg', 1, '2023-05-01');


INSERT INTO product_option (product_id, size, price, is_sold)
VALUES
    (1, '225', 150000, false),
    (1, '230', 160000, false),
    (1, '235', 170000, false),
    (2, '240', 200000, false),
    (3, '245', 180000, false),
    (4, '250', 210000, false),
    (7, '265', 185000, false),
    (8, '270', 230000, false),
    (9, '275', 240000, false),
    (10, '250', 200000, false),
    (11, '285', 270000, false),
    (12, '295', 290000, false),
    (14, '275', 250000, false),
    (15, '300', 350000, false),
    (16, '300', 360000, false),
    (17, '255', 220000, false),
    (18, '300', 370000, false),
    (19, '225', 150000, false),
    (20, '300', 380000, false),
    (21, '250', 200000, false),
    (22, '230', 160000, false),
    (23, '270', 230000, false),
    (24, '260', 210000, false),
    (25, '280', 260000, false),
    (26, '235', 170000, false),
    (27, '295', 300000, false),
    (28, '245', 200000, false),
    (29, '265', 220000, false),
    (30, '290', 310000, false);

INSERT INTO qna (title, content, created_at, member_id) VALUES
                                                                ('신발 반품 정책이 어떻게 되나요?', '신발이 맞지 않으면 반품할 수 있나요?', '2024-11-01 10:00:00', 1),
                                                                ('운동화 배송은 얼마나 걸리나요?', '운동화 배송 소요 시간이 얼마나 걸리나요?', '2024-11-02 12:30:00', 2),
                                                                ('50,000원 이상 구매 시 무료 배송이 되나요?', '50,000원 이상 구매하면 무료 배송 되나요?', '2024-11-03 15:00:00', 1),
                                                                ('주문한 신발을 어떻게 추적하나요?', '신발을 주문한 후 주문 상태를 어떻게 추적하나요?', '2024-11-04 09:45:00', 2);
