INSERT INTO patient (id, name, email, password) VALUES (101, 'João Silva', 'joao@email.com', '123456');
INSERT INTO patient (id, name, email, password) VALUES (102, 'Maria Oliveira', 'maria@email.com', '123456');
INSERT INTO patient (id, name, email, password) VALUES (103, 'Lucas Santos', 'lucas@email.com', '123456');

INSERT INTO doctor (id, name, email, password, specialty) VALUES (1, 'Dr. Roberto Almeida', 'roberto@clinic.com', '123456', 'Cardiologia');
INSERT INTO doctor (id, name, email, password, specialty) VALUES (2, 'Dra. Fernanda Costa', 'fernanda@clinic.com', '123456', 'Dermatologia');
INSERT INTO doctor (id, name, email, password, specialty) VALUES (3, 'Dr. Carlos Mendes', 'carlos@clinic.com', '123456', 'Ortopedia');
INSERT INTO doctor (id, name, email, password, specialty) VALUES (4, 'Dra. Juliana Silva', 'juliana@clinic.com', '123456', 'Pediatria');
INSERT INTO doctor (id, name, email, password, specialty) VALUES (5, 'Dr. Marcos Paulo', 'marcos@clinic.com', '123456', 'Neurologia');

INSERT INTO medical_report (id, name, details, patient_id, doctor_id, report_date) VALUES (1001, 'Eletrocardiograma', 'Ritmo sinusal normal. Sem alterações isquêmicas agudas.', 101, 1, '2023-10-15');
INSERT INTO medical_report (id, name, details, patient_id, doctor_id, report_date) VALUES (1002, 'Exame Dermatológico', 'Presença de nevo atípico no dorso. Recomendada biópsia.', 102, 2, '2023-11-02');
INSERT INTO medical_report (id, name, details, patient_id, doctor_id, report_date) VALUES (1003, 'Raio-X de Joelho', 'Sinais leves de artrose. Sem fraturas evidentes.', 101, 3, '2024-01-20');
INSERT INTO medical_report (id, name, details, patient_id, doctor_id, report_date, is_ai_summarized, ai_points_of_attention, original_file_name) VALUES (1004, 'Ressonância Magnética do Ombro', 'Paciente apresenta histórico de dor no ombro direito. O exame demonstra tendinopatia do supraespinhal.', 101, null, '2024-02-10', true, '["Tendinopatia do músculo supraespinhal confirmada","Ausência de rupturas tendíneas completas","Derrames articulares leves"]', 'ressonancia_ombro_2024.pdf');

INSERT INTO doctor_settings (doctor_id, work_days, start_time, end_time, has_lunch_break, lunch_start, lunch_end) VALUES (1, '1,2,3,4,5', '08:00', '17:00', true, '12:00', '14:00');
INSERT INTO doctor_settings (doctor_id, work_days, start_time, end_time, has_lunch_break, lunch_start, lunch_end) VALUES (2, '1,2,3,4,5,6', '09:00', '19:00', true, '13:00', '14:00');
INSERT INTO doctor_settings (doctor_id, work_days, start_time, end_time, has_lunch_break, lunch_start, lunch_end) VALUES (3, '1,3,5', '10:00', '20:00', false, '12:00', '13:00');
INSERT INTO doctor_settings (doctor_id, work_days, start_time, end_time, has_lunch_break, lunch_start, lunch_end) VALUES (4, '2,4', '08:00', '12:00', false, '12:00', '13:00');
INSERT INTO doctor_settings (doctor_id, work_days, start_time, end_time, has_lunch_break, lunch_start, lunch_end) VALUES (5, '', '08:00', '18:00', true, '12:00', '13:00');

INSERT INTO appointment (doctor_id, patient_id, appointment_date, appointment_time, status) VALUES (1, null, CURRENT_DATE + 1, '09:00', 'available');
INSERT INTO appointment (doctor_id, patient_id, appointment_date, appointment_time, status) VALUES (1, 102, CURRENT_DATE + 1, '10:00', 'booked');
INSERT INTO appointment (doctor_id, patient_id, appointment_date, appointment_time, status) VALUES (1, null, CURRENT_DATE + 1, '11:00', 'available');
INSERT INTO appointment (doctor_id, patient_id, appointment_date, appointment_time, status) VALUES (1, 103, CURRENT_DATE + 1, '14:00', 'booked');
INSERT INTO appointment (doctor_id, patient_id, appointment_date, appointment_time, status) VALUES (1, null, CURRENT_DATE + 2, '09:00', 'available');
INSERT INTO appointment (doctor_id, patient_id, appointment_date, appointment_time, status) VALUES (2, null, CURRENT_DATE + 1, '13:00', 'available');
INSERT INTO appointment (doctor_id, patient_id, appointment_date, appointment_time, status) VALUES (2, null, CURRENT_DATE + 1, '14:00', 'available');
INSERT INTO appointment (doctor_id, patient_id, appointment_date, appointment_time, status) VALUES (2, 101, CURRENT_DATE + 1, '15:00', 'booked');
INSERT INTO appointment (doctor_id, patient_id, appointment_date, appointment_time, status) VALUES (3, 101, CURRENT_DATE + 1, '10:00', 'booked');
INSERT INTO appointment (doctor_id, patient_id, appointment_date, appointment_time, status) VALUES (3, null, CURRENT_DATE + 1, '11:00', 'available');
