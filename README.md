# 🎓 Automated Course Assignment System

A Java-based application that automates course assignment for university students by processing Excel input files, applying constraint-based logic, and generating optimized course assignments as output.

---

## 🚀 Features

- 📥 Import student and course data from Excel files  
- ⚙️ Automatically assign courses based on constraints:
  - Time conflicts  
  - Prerequisites  
  - Course capacity limits  
- 🧠 Apply algorithmic logic (DSA) to handle edge cases and conflicts  
- 📤 Export results to a new Excel file with assigned courses  
- 🖥️ User interface built with Java Swing  

---

## 🎬 Demo

---

## 🛠️ Tech Stack

- **Java**
- **Java Swing** (for UI, if applicable)
- **Apache POI** (Excel processing)
- **Data Structures & Algorithms (DSA)**

---

## 📂 Project Structure

```
src/
├── Main.java            # Entry point
├── ImportPage.java      # Handles Excel input
├── Process.java         # Core assignment logic
├── Export.java          # Generates Excel output
├── ExportPage.java      # Output interface
└── StoreData.java       # Data storage and handling
```

---

## ⚙️ How It Works

1. Upload Excel file containing student and course data  
2. System validates constraints (time, prerequisites, limits)  
3. Assignment algorithm processes data  
4. Output Excel file is generated with assigned courses  

---

## ▶️ How to Run

1. Clone the repository  
   ```bash
   git clone https://github.com/JakeLee111/Automated_Course_Assignment_System.git
2.	Open in IntelliJ / VS Code
3.	Run Main.java
4.	Upload Excel file → System processes → Download output

---

💡 Key Concepts Demonstrated

- Constraint-based system design
- Separation of concerns (input, logic, output)
- File processing using Apache POI
- Modular programming with Java

---

## 🔮 Future Improvements
	•	Add database integration (MySQL)
	•	Build REST API for external integration
	•	Improve optimization algorithm
	•	Deploy as a web application

---

## 👤 Author

Jake Lee
- GitHub: https://github.com/JakeLee111
- LinkedIn: https://www.linkedin.com/in/jakelee11/
