CREATE TABLE IF NOT EXISTS patient (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS doctor (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    specialty VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS medical_report (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    details VARCHAR(500) NOT NULL,
    patient_id BIGINT,
    doctor_id BIGINT,
    report_date DATE,
    is_ai_summarized BOOLEAN,
    ai_points_of_attention VARCHAR(2000),
    original_file_name VARCHAR(255),
    FOREIGN KEY (patient_id) REFERENCES patient(id),
    FOREIGN KEY (doctor_id) REFERENCES doctor(id)
);

CREATE TABLE IF NOT EXISTS doctor_settings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT UNIQUE,
    work_days VARCHAR(255),
    start_time VARCHAR(10),
    end_time VARCHAR(10),
    has_lunch_break BOOLEAN,
    lunch_start VARCHAR(10),
    lunch_end VARCHAR(10),
    FOREIGN KEY (doctor_id) REFERENCES doctor(id)
);

CREATE TABLE IF NOT EXISTS appointment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    doctor_id BIGINT NOT NULL,
    patient_id BIGINT,
    appointment_date DATE NOT NULL,
    appointment_time VARCHAR(10) NOT NULL,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (doctor_id) REFERENCES doctor(id),
    FOREIGN KEY (patient_id) REFERENCES patient(id)
);