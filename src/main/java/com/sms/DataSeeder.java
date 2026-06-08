package com.sms;

import com.sms.model.Competition;
import com.sms.model.TrackRecord;
import com.sms.model.TrackRecord.*;
import com.sms.service.CompetitionService;
import com.sms.service.TrackRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private TrackRecordService trackService;
    @Autowired private CompetitionService compService;

    @Override
    public void run(String... args) {
        seedTrackRecords();
        seedCompetitions();
        System.out.println("✅ Data seeding complete.");
    }

    // ── Helper builders ──────────────────────────────────────────────────────

    private SemesterRecord sem(String name, double gpa, int credits, int att, String... subjects) {
        SemesterRecord s = new SemesterRecord();
        s.setSem(name); s.setGpa(gpa); s.setCredits(credits);
        s.setAttendance(att); s.setSubjects(Arrays.asList(subjects));
        return s;
    }

    private AttendanceSummary att(int total, int present) {
        AttendanceSummary a = new AttendanceSummary();
        a.setTotal(total); a.setPresent(present);
        a.setPercent((int) Math.round((present * 100.0) / total));
        return a;
    }

    private AssignmentSummary asgn(int total, int submitted, int onTime) {
        AssignmentSummary a = new AssignmentSummary();
        a.setTotal(total); a.setSubmitted(submitted); a.setOnTime(onTime);
        return a;
    }

    private ProjectRecord proj(String title, String tech, String grade, int year) {
        ProjectRecord p = new ProjectRecord();
        p.setTitle(title); p.setTech(tech); p.setGrade(grade); p.setYear(year);
        return p;
    }

    private CertificationRecord cert(String name, String issuer, String date, String badge) {
        CertificationRecord c = new CertificationRecord();
        c.setName(name); c.setIssuer(issuer); c.setDate(date); c.setBadge(badge);
        return c;
    }

    private Competition comp(String studentId, String title, String category,
                              String position, String organizer, String date,
                              String prize, String team, String description) {
        Competition c = new Competition();
        c.setStudentId(studentId); c.setTitle(title); c.setCategory(category);
        c.setPosition(position); c.setOrganizer(organizer); c.setDate(date);
        c.setPrize(prize); c.setTeam(team); c.setDescription(description);
        return c;
    }

    private TrackRecord buildRecord(String studentId,
                                     List<SemesterRecord> sems,
                                     AttendanceSummary att,
                                     AssignmentSummary asgn,
                                     List<ProjectRecord> projects,
                                     List<CertificationRecord> certs) {
        TrackRecord r = new TrackRecord();
        r.setStudentId(studentId);
        r.setSemesters(sems);
        r.setAttendance(att);
        r.setAssignments(asgn);
        r.setProjects(projects);
        r.setCertifications(certs);
        return r;
    }

    // ── Seed Track Records ───────────────────────────────────────────────────
    private void seedTrackRecords() {
        String[] ids = {"STU001","STU002","STU003","STU004","STU005","STU006","STU007","STU008"};
        for (String id : ids) {
            if (!trackService.existsForStudent(id)) {
                trackService.save(buildTrackFor(id));
                System.out.println("  Seeded track record for " + id);
            }
        }
    }

    private TrackRecord buildTrackFor(String id) {
        return switch (id) {
            case "STU001" -> buildRecord(id,
                List.of(
                    sem("Sem 1", 8.6, 22, 91, "Math I","Physics","C Programming","English","ES Lab"),
                    sem("Sem 2", 8.9, 24, 88, "Math II","Chemistry","DS","Digital Circuits","Workshop"),
                    sem("Sem 3", 9.1, 24, 93, "DBMS","OS","COA","Discrete Math","OOP"),
                    sem("Sem 4", 9.2, 26, 95, "CN","AI","SE","Elective I","Mini Project")),
                att(240, 221), asgn(48, 46, 44),
                List.of(proj("Smart Attendance System","Python, OpenCV","A+",2024),
                        proj("E-Commerce Platform","React, Node.js","A",2025)),
                List.of(cert("AWS Cloud Practitioner","Amazon","2024-08","☁️"),
                        cert("Python for Data Science","Coursera","2024-03","🐍"),
                        cert("React Developer","Meta","2025-01","⚛️")));

            case "STU002" -> buildRecord(id,
                List.of(
                    sem("Sem 1", 7.8, 22, 85, "Math I","Physics","C Programming","English","ES Lab"),
                    sem("Sem 2", 8.1, 24, 82, "Math II","Signals","Circuit Theory","Digital Circuits","Workshop"),
                    sem("Sem 3", 8.4, 24, 87, "EMT","Analog Circuits","Control Systems","DSP","VLSI"),
                    sem("Sem 4", 8.4, 26, 89, "Microprocessors","Communication","Elective I","Mini Project","Seminar")),
                att(240, 203), asgn(48, 42, 38),
                List.of(proj("IoT Home Automation","Arduino, ESP32","A",2024),
                        proj("VLSI Design of ALU","Verilog, Xilinx","A+",2025)),
                List.of(cert("NPTEL Electronics","NPTEL","2024-06","🔌"),
                        cert("IoT Fundamentals","Cisco","2024-11","📡")));

            case "STU003" -> buildRecord(id,
                List.of(
                    sem("Sem 1", 8.2, 20, 90, "Management Principles","Accounting","Business Math","Communication","IT Basics"),
                    sem("Sem 2", 8.5, 20, 92, "Marketing","HR Management","Finance","Business Law","Statistics"),
                    sem("Sem 3", 8.7, 22, 94, "Operations","Strategy","Entrepreneurship","Elective","Case Studies"),
                    sem("Sem 4", 8.7, 22, 88, "Research Methods","Business Analytics","Capstone Project","Internship")),
                att(200, 184), asgn(40, 39, 37),
                List.of(proj("Market Entry Strategy — EV Sector","Research, Presentation","A+",2024),
                        proj("HR Analytics Dashboard","Excel, Power BI","A",2025)),
                List.of(cert("Google Analytics","Google","2024-05","📊"),
                        cert("PMP Foundation","PMI","2025-01","📋")));

            case "STU004" -> buildRecord(id,
                List.of(
                    sem("Sem 1", 7.0, 22, 80, "Calculus","Linear Algebra","Discrete Math","Statistics","Programming"),
                    sem("Sem 2", 7.2, 22, 78, "Analysis","Abstract Algebra","Probability","Numerical Methods","Lab"),
                    sem("Sem 3", 7.5, 24, 83, "Topology","Complex Analysis","Graph Theory","Elective","Seminar"),
                    sem("Sem 4", 7.5, 24, 85, "Research Project","Advanced Stats","Operations Research","Elective")),
                att(220, 181), asgn(44, 40, 36),
                List.of(proj("Graph Coloring Algorithms","Python, NetworkX","B+",2024),
                        proj("Statistical Analysis of Climate Data","R, ggplot2","A",2025)),
                List.of(cert("SAS Statistical Analysis","SAS","2024-09","📉")));

            case "STU005" -> buildRecord(id,
                List.of(
                    sem("Sem 1", 9.4, 22, 97, "Math I","Physics","C Programming","English","ES Lab"),
                    sem("Sem 2", 9.6, 24, 98, "Math II","Chemistry","DS","Digital Circuits","Workshop"),
                    sem("Sem 3", 9.8, 24, 99, "DBMS","OS","COA","Discrete Math","OOP"),
                    sem("Sem 4", 9.8, 26, 100, "ML","CN","SE","Elective I","Capstone Project")),
                att(240, 238), asgn(48, 48, 48),
                List.of(proj("AI Chatbot for Education","Python, GPT API","A+",2024),
                        proj("Blockchain Voting System","Solidity, React","A+",2025),
                        proj("Real-time Object Detection","YOLO, TensorFlow","A+",2025)),
                List.of(cert("Google Cloud Professional","Google","2024-04","☁️"),
                        cert("TensorFlow Developer","Google","2024-08","🤖"),
                        cert("GitHub Actions CI/CD","GitHub","2025-02","🔧"),
                        cert("Kubernetes Associate","CNCF","2025-03","⚙️")));

            case "STU006" -> buildRecord(id,
                List.of(
                    sem("Sem 1", 7.9, 22, 86, "Math I","Physics","C Programming","English","ES Lab"),
                    sem("Sem 2", 8.0, 24, 84, "Math II","Linear Algebra","DS","AI Basics","Workshop"),
                    sem("Sem 3", 8.1, 24, 88, "ML","DL","NLP","Computer Vision","Research Methods"),
                    sem("Sem 4", 8.1, 26, 87, "Advanced ML","Reinforcement Learning","Thesis","Seminar")),
                att(240, 206), asgn(48, 44, 41),
                List.of(proj("Sentiment Analysis","BERT, HuggingFace","A",2024),
                        proj("Autonomous Driving Simulation","CARLA, PyTorch","A+",2025)),
                List.of(cert("Deep Learning Specialization","Coursera","2024-07","🧠"),
                        cert("NLP with Python","Udemy","2024-12","💬")));

            case "STU007" -> buildRecord(id,
                List.of(
                    sem("Sem 1", 6.8, 22, 79, "Math I","Classical Mechanics","Lab I","English","Programming"),
                    sem("Sem 2", 7.0, 22, 80, "Electrodynamics","Optics","Thermodynamics","Lab II","Stats"),
                    sem("Sem 3", 7.2, 24, 82, "Quantum Mechanics","Nuclear Physics","Solid State","Lab III","Elective"),
                    sem("Sem 4", 7.2, 24, 83, "Astrophysics","Particle Physics","Research Project","Seminar")),
                att(220, 178), asgn(44, 38, 34),
                List.of(proj("Quantum Computing Simulation","Qiskit, Python","B+",2024),
                        proj("Solar System Simulation","MATLAB, Python","A",2025)),
                List.of(cert("Quantum Computing Foundations","IBM","2025-01","⚛️")));

            case "STU008" -> buildRecord(id,
                List.of(
                    sem("Sem 1", 6.4, 20, 75, "Management Principles","Accounting","Business Math","Communication","IT Basics"),
                    sem("Sem 2", 6.6, 20, 77, "Marketing","HR Management","Finance","Business Law","Statistics"),
                    sem("Sem 3", 6.8, 22, 79, "Operations","Strategy","Entrepreneurship","Elective","Case Studies"),
                    sem("Sem 4", 6.8, 22, 80, "Research Methods","Business Analytics","Capstone Project","Internship")),
                att(200, 153), asgn(40, 34, 29),
                List.of(proj("Supply Chain Optimization","Excel, Tableau","B",2024),
                        proj("Digital Marketing Campaign","Google Ads, Analytics","B+",2025)),
                List.of(cert("HubSpot Marketing","HubSpot","2024-10","📣")));

            default -> buildRecord(id, List.of(), att(0,0), asgn(0,0,0), List.of(), List.of());
        };
    }

    // ── Seed Competitions ────────────────────────────────────────────────────
    private void seedCompetitions() {
        if (!compService.hasCompetitions("STU001")) {
            List<Competition> all = List.of(
                // STU001
                comp("STU001","National Hackathon 2024","Hackathon","🥇 1st Place","HackerEarth","2024-09-14","₹50,000","CodeStorm","Built an AI-powered disaster relief coordination app in 36 hours."),
                comp("STU001","CodeChef Long Challenge","Competitive Coding","🏆 Top 100","CodeChef","2024-11-01","Certificate","Solo","Ranked 87th globally out of 12,000+ participants."),
                comp("STU001","Inter-College Tech Quiz","Quiz","🥈 2nd Place","IIT Bombay","2025-01-20","₹10,000","Team Alpha","Technical quiz covering CS fundamentals, AI, and emerging tech."),
                comp("STU001","Smart India Hackathon","Hackathon","🎖️ Finalist","AICTE","2025-03-10","Certificate","InnovateTech","Developed a blockchain-based document verification system."),
                comp("STU001","ACM ICPC Regionals","Competitive Coding","🥉 3rd Place","ACM","2025-04-05","₹15,000","ByteBusters","Solved 8/12 problems in competitive programming contest."),
                // STU002
                comp("STU002","Robocon 2024","Robotics","🥇 1st Place","DD Robocon","2024-08-20","₹1,00,000","MechWarriors","Designed and built autonomous robots for precision tasks."),
                comp("STU002","Circuit Design Challenge","Technical","🥈 2nd Place","IEEE","2024-12-05","₹20,000","CircuitBros","Optimized power-efficient circuit for IoT applications."),
                comp("STU002","Electronics Innovation Award","Innovation","🏅 Special Award","TechFest IIT-B","2025-01-15","₹25,000","Solo","Awarded for innovative use of FPGA in real-time audio processing."),
                // STU003
                comp("STU003","National B-Plan Competition","Business","🥇 1st Place","IIM Ahmedabad","2024-10-18","₹75,000","VentureX","Pitched a sustainable agri-tech startup to a panel of top VCs."),
                comp("STU003","Marketing Strategy Olympiad","Marketing","🥈 2nd Place","XLRI","2025-02-11","₹20,000","BrandBuilders","Developed go-to-market strategy for a D2C health brand."),
                comp("STU003","Case Study Competition — Finance","Finance","🥉 3rd Place","ISB Hyderabad","2025-04-02","₹10,000","FinanceFirst","Analysed and presented M&A valuation case study."),
                // STU004
                comp("STU004","Mathematics Olympiad","Academic","🥉 3rd Place","NBHM","2024-11-30","Medal","Solo","National-level olympiad covering real analysis and number theory."),
                comp("STU004","Statistics Poster Competition","Research","🏅 Best Poster","ISI Kolkata","2025-03-22","Certificate + ₹5,000","Solo","Presented probabilistic model for epidemic spread."),
                // STU005
                comp("STU005","Google Hackathon 2024","Hackathon","🥇 Winner","Google India","2024-07-22","₹2,00,000","NeuralNinjas","Built Gemini-powered accessibility tool for visually impaired students."),
                comp("STU005","ICPC World Finals Qualifier","Competitive Coding","🏆 Qualified","ACM ICPC","2024-10-10","Scholarship","Team Prime","One of only 15 teams from India to qualify for World Finals."),
                comp("STU005","Microsoft Imagine Cup","Innovation","🥈 Runner Up","Microsoft","2025-01-08","₹50,000","CogniCode","AI-driven mental health monitoring wearable app."),
                comp("STU005","TCS Codevita Season 12","Competitive Coding","🥇 Rank 1 (India)","TCS","2025-02-28","PPO + ₹1,00,000","Solo","Topped the national leaderboard with perfect score in 5 rounds."),
                comp("STU005","IEEE Student Paper Award","Research","🏅 Best Paper","IEEE","2025-04-12","₹30,000","Solo","Research paper on transformer architecture optimization accepted."),
                // STU006
                comp("STU006","AI/ML Hackathon — Flipkart","Hackathon","🥈 Runner Up","Flipkart","2024-09-05","₹40,000","DeepMind INC","Built real-time product recommendation engine with 93% accuracy."),
                comp("STU006","DataHack Summit","Data Science","🥉 3rd Place","Analytics Vidhya","2024-12-14","₹15,000","Solo","Time-series forecasting challenge on energy consumption data."),
                // STU007
                comp("STU007","INSPIRE Science Award","Science","🏅 Selected","DST India","2024-08-08","Scholarship","Solo","Selected for DST INSPIRE scholarship for outstanding science aptitude."),
                comp("STU007","Physics Bowl International","Academic","🥈 2nd (Regional)","AAPT","2025-03-15","Certificate","Solo","High-school and undergrad physics rapid-fire competition."),
                // STU008
                comp("STU008","Youth Entrepreneur Award","Business","🥉 3rd Place","CII Young India","2024-11-11","₹8,000","Solo","Pitched a micro-lending platform for rural women entrepreneurs.")
            );
            compService.saveAll(all);
            System.out.println("  Seeded " + all.size() + " competitions.");
        }
    }
}
