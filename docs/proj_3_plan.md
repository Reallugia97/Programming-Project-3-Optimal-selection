# **CS 2430 — Project 3 Plan**

**Reminder\:** Update at least twice per project; for each “Done” task, include an evidence pointer.

## **1\) Project Information**

* Course / Term: CS 2430 / Fall 2026  
* Project: Programming Project 3: Optimal Selection
* Team Name: Rocket Escape
* Start Date: October 29th 2026
* Due Date: November 15th 2026  
* Checkpoint Date (mid-project): November 8th 2026

## 

## **2\) Team & Roles (assigned for this project)**

| Team Member | Role  | Primary Responsibilities  | Availability / Notes |
| :---- | :---- | :---- | :---- |
| (Shared) | Implementation Lead | Owns core algorithm implementation; integrates modules; maintains main branch health. |  |
| Nathan Johnson | Verification Lead | Creates test plan; edge cases; validates outputs; documents verification evidence. |  |
| Brayden ﻿Bigelow | Communications Lead | Assembles report/video; ensures run instructions; tracks deliverables; merges documentation. |  |

## 

## **3\) Repository & Access (Required for Projects 1–4)**

* Platform: GitHub   
* Repository URL: https://github.com/Reallugia97/Programming-Project-3-Optimal-selection.git
* Instructor invited as viewer by checkpoint?:   
* Repo Structure Requirement (summary): Include /docs with pseudocode, UML/diagram, design notes; keep updated at checkpoint.

## 

## **4\) Milestones (Timeline)**

| Milestone | Target Date | Owner | Definition of Done | Status (NS/IP/D) | Evidence Link / Pointer |
| :---- | :---- | :---- | :---- | :---- | :---- |
| Repo set up \+ skeleton running |  | Brayden | Repo created; README run steps; sample I/O runs. | D | Commit eb77299 |
| Core implementation complete |  | Team | All required features implemented; compiles/runs end-to-end. | IP |  |
| Verification complete |  | Nathan | Test suite covers edge cases; expected vs actual documented. | IP |  |
| Report/video draft complete |  | Team | Draft report sections complete; video outline \+ script notes. |  |  |
| Final integration \+ submission |  | Team | All deliverables finalized; tags release; submission package ready. |  |  |

## 

## 

## 

## **5\) Task Backlog (Assignments \+ Status)**

| ID | Task | Owner | Due Date | Status (NS/IP/D) | Evidence Link / Pointer | Notes / Dependencies |
| :---- | :---- | :---- | :---- | :---- | :---- | :---- |
| 1 | Create repository \+ baseline README/run steps | Brayden | November 3rd | D | Commit 4c04b40 |  |
| 2 | Draft pseudocode for core algorithm in /docs | Nathan | November 8th | D | Commit 78b2603, Commit 077bf22 |  |
| 3 | Draft UML/module diagram in /docs | Team |  | IP | Commit 929b1d8 |  |
| 4 | Implement greedy strategies and brute force | Brayden | November 8th | D | Commit 412d69c, Commit 355ee3d |  |
| 5 | Define verification plan (tests \+ edge cases) | Nathan |  |  |  |  |
| 6 | Implement edge-case tests \+ expected outputs | Nathan |  | IP |  |  |
| 7 | Design notes: assumptions, invariants, tradeoffs |  |  |  |  |  |
| 8 | Checkpoint package: update plan \+ docs \+ invite verification | Team |  | IP |  | Checkpoint submission |
| 9 | Report draft (problem/design/verification/results) | Brayden |  | IP |  |  |
| 10 | Final polish: run instructions \+ release tag | Team |  |  |  | Before submission |

## 

## **6\) Verification Plan**

* Correctness checks/tests to run

* Unit tests for core functions (parsing, core algorithm steps).  
* Golden-file tests: compare outputs to expected results on provided sample inputs.  
* Negative tests: invalid inputs produce clear error messages.

* Verify edge cases.

* Expected vs. actual outputs (where applicable)

* Document expected outputs for at least 3 representative test cases in docs/test-results.md.  
* Include screenshots or terminal output snippets if tooling is minimal.

* Performance checks (if required by the project)

* Confirm no unnecessary O(n^2) loops on hot paths where avoidable.

## **7\) Risks / Blockers**

| Risk / Blocker | Impact | Mitigation Plan | Owner | Status |
| :---- | :---- | :---- | :---- | :---- |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
