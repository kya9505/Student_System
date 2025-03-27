<div align="center">
    <img src="https://capsule-render.vercel.app/api?type=wave&color=FFF4B2&height=180&text=Student%20System&animation=&fontColor=3B3B3B&fontSize=70"
        style="width: 100%; height: auto; display: block; margin: 0;" />
</div>


<div align="left">
    <h2 style="border-bottom: 1px solid #d8dee4; color: #282d33;"> 프로젝트 소개 </h2>
    <div style="font-weight: 700; font-size: 15px; text-align: center; color: #282d33;">
        <h3> Student System </h3>
        <p>이 시스템은 학생 정보를 효율적으로 관리하는 도구로 학생 정보 추가, 수정, 삭제, 문서화 등을 손쉽게 처리할 수 있는 CLI기반 프로그램입니다.
    </div>
</div> <br>

<div align="left">
    <h2 style="border-bottom: 1px solid #d8dee4; color: #282d33;"> 👥 팀원 소개 </h2>
    <div style="font-weight: 700; font-size: 15px; text-align: center; color: #282d33;">
        <p>강창선 <a href="https://github.com/KangChangSeon" target="_blank">#KangChangSeon</a> - 팀장
        <p>고윤아 <a href="https://github.com/kya9505" target="_blank">#kya9505</a> 
        <p>정명채 <a href="https://github.com/jyngmyungchae" target="_blank">#jyngmyungchae</a> 
        <p>정난희 <a href="https://github.com/Eveieve" target="_blank">#Eveieve</a> 
    </div>
</div> <br>


<div align="left">
    <h2 style="border-bottom: 1px solid #d8dee4; color: #282d33;"> 🎯 주요 기능 </h2>
    <div style="font-weight: 700; font-size: 15px; text-align: center; color: #282d33;">
         <strong>add student info  :</strong>  학생 정보 추가 등록  <br>
            <strong>view student info :</strong> 학생 정보 조회 <br>
            <strong>search student info :</strong> 학생 정보 검색<br>
            <strong>sort student info:</strong>학생 정보 정렬  <br>
            <strong>backup to file info:</strong> 학생 정보를 text 파일에 문서화 하고 DB에 저장합니다. <br>
    </div> <br>
    </div> 
</div>


<div align="left">
    <h2 style="border-bottom: 1px solid #d8dee4; color: #282d33;"> 📁 프로젝트 폴더 구조 </h2>
</div>

```plaintext
│── .github
│── .idea
│── out
│── p0215                #버전 관리 파일 
│── p0216
│    │── Main            #실행파일
│    │── ObjectIO       
│    │── SearchStudent   #Search 기능을 정의한 interface
│    │── StudentDAO      #student DAO
│    │── Student         #student DTO
│    │── StudentDBIO     #StudentIO를 구현하고 ObjectIO를 상속받는 class
│    │── StudentFileIO   #학생정보를 text파일에 저장, 수정 등의 기능을 구현
│    │── StudentInput    #학생 정보를 DB에 등록하는 기능을 정의한  interface
│    │── StudentOutput   #DB에 저장된 학생정보를 가져오는 기능을 정의한  interface
│    │── StudentIO       #student 관련 기능을 상속받는 interface
│    │── StudentManager  #실행파일
│── Main            #실행파일
│── ObjectIO       
│── SearchStudent   #Search 기능을 정의한 interface
│── Student         #student DTO
│── StudentDBIO     #StudentIO를 구현하고 ObjectIO를 상속받는 class
│── StudentInput    #학생 정보를 DB에 등록하는 기능을 정의한  interface
│── StudentOutput   #DB에 저장된 학생정보를 가져오는 기능을 정의한  interface
│── StudentIO       #student 관련 기능을 상속받는 interface
│── StudentManager  #실행파일
```

<div align="left">
    <h2 style="border-bottom: 1px solid #d8dee4; color: #282d33;"> 🎮 실행 방법 </h2>
    <div style="font-weight: 700; font-size: 15px; text-align: center; color: #282d33;">
        1️⃣ Java 17+ & MySQL 설치<br>
        2️⃣ hr-finder폴더의 BackupTrigger.SQL, DepartmentTrigger.SQL, procedure.sql 파일 실행  <br>
        3️⃣ 프로그램 실행<br>
        mvn compile exec:java -Dexec.mainClass="com.hr-finder.Main"<br>
        4️⃣ CLI 메뉴 선택 <br><br>
  1. add student info<br>
2. view student info<br>
3. search student info<br>
4. sort student info<br>
5. backup to file<br>
6. exit<br>
choice menu_<br>
    </div>
</div> <br>
<div align="left">
    <h2 style="border-bottom: 1px solid #d8dee4; color: #282d33;"> 🛠️ Tech Stacks </h2>
    <div style="margin: 0 auto; text-align: center;">
        <img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=Java&logoColor=white">
        <img src="https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white">
        <img src="https://img.shields.io/badge/Github-181717?style=for-the-badge&logo=Github&logoColor=white">
        <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white">
    </div>
</div>
<div align="left">
    <h2 style="border-bottom: 2px solid #4CAF50; color: #333; font-size: 24px; padding-bottom: 10px;">
        👥 기여 방법 (팀원 전용)
    </h2>
    <div style="font-weight: 600; font-size: 16px; text-align: left; color: #333; line-height: 1.6; background-color: #f4f4f4; padding: 20px; border-radius: 8px;">
        이 프로젝트는 팀원들만 기여할 수 있습니다.<br><br>
        <ul style="padding-left: 20px;">
            <li><strong>1.</strong> 기능 추가 전 팀원과 논의</li>
            <li><strong>2.</strong> <code>feature/기능명</code> 브랜치 생성 후 개발</li>
            <li><strong>3.</strong> <code>dev</code> 브랜치로 PR 후 코드 리뷰</li>
            <li><strong>4.</strong> 승인되면 <code>main</code> 브랜치에 병합</li>
            <li><strong>5.</strong> 커밋 메시지 형식:
                <ul style="padding-left: 20px;">
                    <li><code>[feat] 기능 추가</code></li>
                    <li><code>[fix] 오류 수정</code></li>
                </ul>
            </li>
        </ul>
    </div>
</div>


<div align="left">
    <h2 style="border-bottom: 1px solid #d8dee4; color: #282d33;"> 📜 라이선스 </h2>
    <div style="font-weight: 700; font-size: 15px; text-align: center; color: #282d33;">
        🔒 팀원 전용 프로젝트입니다. 외부 사용은 제한됩니다.
</div> <br>
<div align="left">>
    <h2 style="border-bottom: 1px solid #d8dee4; color: #282d33;"> 🧑‍💻 Contact me </h2>
    <p>문의 사항이 있으시면 언제든지 연락 주세요!</p>
</div>
