# 📈 주가 확인 프로그램

이 프로젝트는 **Yahoo Finance**에서 제공하는 주가 및 거래량 데이터와
**CNBC**에서 제공하는 RSS 피드를 활용하여
당일 뉴스 이슈와 산업별 주가 변동 간의 관계를 분석하는 것을 목표로 한 프로젝트입니다.

메인 페이지에서 최신 뉴스를 실시간으로 확인할 수 있으며,
해당 날짜의 뉴스가 기술, 건강, 금융, 에너지, 기초 산업 등 각 산업 분야의
주가 변동에 어떤 영향을 미쳤는지 시각적으로 확인할 수 있습니다.

## 💻예시 화면
<b> 1. 메인 화면</b>
<img width="2879" height="1526" alt="image" src="https://github.com/user-attachments/assets/33b5ef49-050f-45d7-896f-75775423768b" />
<b> 2. 주가 확인 차트</b>
<img width="2879" height="1483" alt="image" src="https://github.com/user-attachments/assets/8df1cdff-ce91-434c-ba3f-9cc69e087b57" />
<b> 3. 마이페이지</b>
<img width="2879" height="1530" alt="image" src="https://github.com/user-attachments/assets/ac914cda-6f11-4228-a625-9c0ceee843f8" />
<b> 4. 로그인 화면</b>
<img width="2850" height="1524" alt="image" src="https://github.com/user-attachments/assets/ecadfddf-0310-4df4-b3a0-8cb3ae4f8469" />
<b> 5. 회원가입 화면</b>
<img width="2839" height="1533" alt="image" src="https://github.com/user-attachments/assets/23dcfc06-86b6-4b30-a913-c69d6c91c2c6" />
<b> 6. 비밀번호 변경 페이지</b>
<img width="2848" height="1525" alt="image" src="https://github.com/user-attachments/assets/fff46072-1821-4818-8cfa-214d758adfb0" />


🛠️ 주요 기능

- 기술, 건강, 금융, 에너지, 기초 산업 분야별 상위 10개 기업의 주가 차트 제공
- 기간 선택 기능<br>
  (당일, 5일, 1개월, 3개월, 6개월, 1년, 5년)
- 차트 검색 기능을 통해 사용자가 원하는 종목 직접 조회 가능
- 메인 화면에서 버튼 클릭 시 CNBC RSS 피드를 통해 최신 뉴스 수집
- 이메일 인증 기반 회원가입 기능
- 로그인 / 로그아웃 기능
- 비밀번호 재설정 기능
- 마이페이지에서 프로필 사진 변경, 회원 정보 확인, 회원 탈퇴 기능 제공


## ⭐기술 스택
- BackEnd : Spring Boot
- Backend Template Engine : Thymeleaf
- FrontEnd : HTML, CSS, JavaScript, Chart.js
- Database : MySQL

 ## 🛡️ 보완할 점 및 향후 계획
- 정보 수정, 모의 주식 거래, 구매 내역, 찜 목록 기능 미구현<br>
  → 추후 단계적으로 기능 확장 예정
- 뉴스 데이터를 불러오는 데 시간이 다소 소요됨<br>
  → 캐싱 또는 비동기 처리 방식으로 성능 개선 예정
