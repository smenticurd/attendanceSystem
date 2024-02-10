-- 1. Person
CREATE TABLE person (
                        id serial PRIMARY KEY,
                        first_name VARCHAR(255) NOT NULL,
                        last_name VARCHAR(255) NOT NULL,
                        middle_name VARCHAR(255),
                        login VARCHAR(255) NOT NULL UNIQUE,
                        email VARCHAR(255) NOT NULL UNIQUE
);
CREATE TABLE role (
                      role_id serial PRIMARY KEY,
                      role VARCHAR(255) NOT NULL,
                      description TEXT
);

-- 2. Person Info


CREATE TABLE person_role (
                             person_id INT,
                             role_id INT,
                             PRIMARY KEY (person_id, role_id),
                             FOREIGN KEY (person_id) REFERENCES person(id),
                             FOREIGN KEY (role_id) REFERENCES role(role_id)
);

-- 4. Person Authority
CREATE TABLE person_authority (
                                  person_authority_id serial PRIMARY KEY,
                                  person_id INT NOT NULL,
                                  last_login date,
                                  password_hash TEXT NOT NULL,
                                  active BOOLEAN NOT NULL,
                                  user_role_id INT,
                                  password_refresh_date DATE,
                                  isRefreshed BOOLEAN NOT NULL,
                                  FOREIGN KEY (person_id) REFERENCES person(id),
                                  FOREIGN KEY (user_role_id) REFERENCES role(role_id)
);


CREATE TABLE specialty (
                           specialty_id serial PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           code VARCHAR(50) NOT NULL
);
CREATE TABLE course (
                        course_id serial PRIMARY KEY,
                        speciality_id INT,
                        code VARCHAR(50) NOT NULL,
                        name VARCHAR(255) NOT NULL,
                        description TEXT,
                        FOREIGN KEY (speciality_id) REFERENCES specialty(specialty_id)
);
CREATE TABLE person_info (
                             person_info_id serial PRIMARY KEY,
                             person_id INT NOT NULL,
                             gender VARCHAR(255) NOT NULL ,
                             image bytea ,
                             telephone VARCHAR(255)NOT NULL ,
                             birth_date DATE NOT NULL ,
                             specialty_id INT,
                             FOREIGN KEY (person_id) REFERENCES person(id),
                             FOREIGN KEY (specialty_id) REFERENCES specialty(specialty_id)
);
CREATE TABLE class_room (
                             class_room_id serial PRIMARY KEY,
                             room_number VARCHAR(50) NOT NULL UNIQUE
 );


CREATE TABLE section (
                         section_id serial PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         course_id INT,
                         schedule_id INT,
                         person_id INT, -- Teacher
                         quota INT,
                         count INT,
                         type VARCHAR(255),
                         related_section_name VARCHAR(255),
                         FOREIGN KEY (course_id) REFERENCES course(course_id),
                         FOREIGN KEY (person_id) REFERENCES person(id)
);

CREATE TABLE schedule (
                          schedule_id serial PRIMARY KEY,
                          section_id INT,
                          class_room_id INT,
                          day_of_week INT NOT NULL,
                          start_time TIME,
                          total_hours INT,
                          FOREIGN KEY (section_id) REFERENCES section(section_id),
                          FOREIGN KEY (class_room_id) REFERENCES class_room(class_room_id)
);
CREATE TABLE reason_for_absence (
                                    reason_id serial PRIMARY KEY,
                                    person_id INT,
                                    section_id INT,
                                    description TEXT,
                                    document TEXT,
                                    status VARCHAR(255),
                                    isAccepted BOOLEAN,
                                    FOREIGN KEY (person_id) REFERENCES person(id),
                                    FOREIGN KEY (section_id) REFERENCES section(section_id)
);




-- 11. Attendance Record
CREATE TABLE attendance_record (
                                   record_id serial PRIMARY KEY,
                                   student_id INT,
                                   schedule_id INT,
                                   total_seating_hours INT,
                                   current_week INT,
                                   FOREIGN KEY (student_id) REFERENCES person(id),
                                   FOREIGN KEY (schedule_id) REFERENCES schedule(schedule_id)
);

-- 12. Reason For Absence

-- 13. Attendance Info
CREATE TABLE Attendance_info (
                                 attendance_info_id serial PRIMARY KEY,
                                 person_id INT,
                                 record_type VARCHAR(255) NOT NULL,
                                 percent DECIMAL(5, 2) NOT NULL,
                                 section_id INT NOT NULL,
                                 FOREIGN KEY (person_id) REFERENCES person(id),
                                 FOREIGN KEY (section_id) REFERENCES section(section_id)
);