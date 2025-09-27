-- Select the database
USE defiut;

-- Drop tables if they exist
DROP TABLE IF EXISTS ChallengeTag;
DROP TABLE IF EXISTS Tag;
DROP TABLE IF EXISTS Completed;
DROP TABLE IF EXISTS Granted;
DROP TABLE IF EXISTS Challenge;
DROP TABLE IF EXISTS Badge;
DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Achievement;
DROP TABLE IF EXISTS Awarded;

-- Create table User
CREATE TABLE User (
    userId INT NOT NULL AUTO_INCREMENT,
    userNickname VARCHAR(255) NOT NULL,
    userEmail VARCHAR(255) NOT NULL,
    userPassword VARCHAR(255) NOT NULL,
    userRole VARCHAR(255) NOT NULL,
    userScore INT,
    PRIMARY KEY (userId),
    UNIQUE (userNickname),
    UNIQUE (userEmail),
    CHECK (userRole IN ('ADMIN', 'USER'))
);

-- Create table Badge
CREATE TABLE Challenge (
    challengeId INT NOT NULL AUTO_INCREMENT,
    challengeName VARCHAR(255) NOT NULL,
    challengeDate DATE NOT NULL,
    challengeDifficulty VARCHAR(255) NOT NULL,
    challengeLanguage VARCHAR(255) NOT NULL,
    challengeDescription TEXT,
    challengeFlag VARCHAR(255) NOT NULL,
    challengeDocker INT NOT NULL,
    challengePoints INT NOT NULL,
    PRIMARY KEY (challengeId),
    CHECK (challengeDifficulty IN ('EASY', 'MEDIUM', 'HARD')),
    CHECK (challengePoints >= 0)
);

-- Create table Badge
CREATE TABLE Granted (
    grantedUser INT NOT NULL,
    grantedBadgeRank INT NOT NULL,
    grantedDate DATE NOT NULL,
    PRIMARY KEY (grantedUser, grantedBadgeRank),
    FOREIGN KEY (grantedUser) REFERENCES User(userId),
    CHECK (grantedBadgeRank IN (1, 2, 3))
);

-- Create table Completed
CREATE TABLE Completed (
    completedUser INT NOT NULL,
    completedChallenge INT NOT NULL,
    completedStatus VARCHAR(255) NOT NULL,
    completedDate DATE NOT NULL,
    PRIMARY KEY (completedUser, completedChallenge),
    FOREIGN KEY (completedUser) REFERENCES User(userId),
    FOREIGN KEY (completedChallenge) REFERENCES Challenge(challengeId),
    CHECK (completedStatus IN ('STARTED', 'COMPLETED'))
);

-- Create table Tag
CREATE TABLE Tag (
    tagId INT NOT NULL AUTO_INCREMENT,
    tagName VARCHAR(255) NOT NULL,
    tagColor VARCHAR(255) NOT NULL,
    PRIMARY KEY (tagId),
    UNIQUE (tagName)
);

-- Create table ChallengeTag
CREATE TABLE ChallengeTag (
    challengeTagChallenge INT NOT NULL,
    challengeTagTag INT NOT NULL,
    PRIMARY KEY (challengeTagChallenge, challengeTagTag),
    FOREIGN KEY (challengeTagChallenge) REFERENCES Challenge(challengeId),
    FOREIGN KEY (challengeTagTag) REFERENCES Tag(tagId)
);
-- Create table Achievement
CREATE TABLE `Achievement` (
  `achievementId` int NOT NULL,
  `achievementColor` varchar(7) NOT NULL,
  `achievementTitle` varchar(100) NOT NULL,
  `achievementDescription` varchar(300) NOT NULL
);

-- Create table Awarded
CREATE TABLE `Awarded` (
  `awardedUserId` int NOT NULL,
  `awardedAchievementId` int NOT NULL,
  `awardedDate` date NOT NULL
);

-- Delete all data from tables
DELETE FROM Granted;
DELETE FROM ChallengeTag;
DELETE FROM Tag;
DELETE FROM Completed;
DELETE FROM User;
DELETE FROM Challenge;
DELETE FROM Achievement;
DELETE FROM Awarded;

-- Insert users with static IDs
INSERT INTO User (userId, userNickname, userEmail, userPassword, userRole, userScore) 
VALUES 
    (58, 'JohnDoe', 'john.doe@email.com', '75K3eLr+dx6JJFuJ7LwIpEpOFmwGZZkRiB84PURz6U8=', 'USER', 7),
    (59, 'AliceSmith', 'alice.smith@email.com', '+7SooWP/qVi08Cv5yrswz++0DegD8sTDRqnTmzvhtUQ=', 'USER', 4),
    (60, 'BobJohnson', 'bob.johnson@email.com', '2nZVtb9nA5w+dqmdjm+2lpNwu8D6RAyuaZzxo+Lx4KE=', 'USER', 4),
    (61, 'EmilyBrown', 'emily.brown@email.com', 'xrqRuQ2SLhWYk/RsOH5dwbPcXBAaWkUi8DuYcXeiSpE=', 'USER', 1),
    (62, 'DanielLee', 'daniel.lee@email.com', 'Lo7cvwkatqOa5VoMnHoA95PDloOJiTfYdg4EAXyY0tg=', 'ADMIN', 2),
    (63, 'OliviaTaylor', 'olivia.taylor@email.com', 'bg2qCnktyu8kc4mEJnsFxRU2Y/FvoxqWMlqy/BynE7g=', 'USER', 1000),
    (64, 'MichaelJones', 'michael.jones@email.com', 'Y3tjiCvRqHRfwIlfPqajiE5wlNfddMd30hLwC5CqrXo=', 'USER', 640),
    (65, 'SophiaDavis', 'sophia.davis@email.com', 'O2Bnn7Z/W9avL/xfwFGYEmK9ABkPOpqwEVUOxCSVhrE=', 'ADMIN', 0),
    (66, 'RyanWilson', 'ryan.wilson@email.com', 'g1foesKU5QeXD32sLHkmT5ECSm2UpfXim2Flu7zpxLw=', 'ADMIN', 0),
    (67, 'EmmaClark', 'emma.clark@email.com', 'j3fj5hMUBByNTIn8pWIcXz+Q0imyHOeydimdqjFjRbo=', 'USER', 3),
    (1, 'admin', 'admin@localhost', 'jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=', 'ADMIN', 0);

-- Insert challenges with static IDs
INSERT INTO Challenge (challengeId, challengeName, challengeDate, challengeDifficulty, challengeLanguage, challengeDescription, challengeFlag, challengeDocker, challengePoints)
VALUES 
    (63, 'AlgorithmChallenge1', '2023-01-15', 'EASY', 'PYTHON', 'Solve basic algorithm problems', 'flag1', 0, 47),
    (64, 'WebDevelopmentChallenge', '2023-02-20', 'MEDIUM', 'JAVASCRIPT', 'Build a simple web application', 'flag2', 0, 85),
    (65, 'DataStructuresChallenge', '2023-03-10', 'HARD', 'JAVA', 'Implement common data structures', 'flag3', 0, 111),
    (66, 'CodeReviewChallenge', '2023-04-05', 'MEDIUM', 'C', 'Review and improve code quality', 'flag4', 0, 63),
    (67, 'MachineLearningChallenge', '2023-05-12', 'HARD', 'PYTHON', 'Create a machine learning model', 'flag5', 0, 120),
    (68, 'MobileAppChallenge', '2023-06-08', 'MEDIUM', 'PYTHON', 'Develop a mobile app', 'flag6', 0, 75),
    (69, 'NetworkingChallenge', '2023-07-20', 'EASY', 'C', 'Set up basic network configurations', 'flag7', 0, 50),
    (70, 'SecurityChallenge', '2023-08-15', 'HARD', 'PYTHON', 'Implement security measures', 'flag8', 0, 130),
    (71, 'DatabaseChallenge', '2023-09-25', 'MEDIUM', 'SQL', 'Design and query a database', 'flag9', 0, 90),
    (72, 'UIUXChallenge', '2023-10-30', 'EASY', 'HTML', 'Create a user-friendly interface', 'flag10', 0, 40),
    (73, 'AlgorithmChallenge2', '2023-12-31', 'EASY', 'PYTHON', 'Solve more algorithm problems', 'flag11', 0, 50),
    (74, 'WebDevelopmentChallenge2', '2023-12-20', 'MEDIUM', 'JAVASCRIPT', 'Enhance a web application', 'flag12', 0, 80),
    (75, 'DataStructuresChallenge2', '2024-01-01', 'HARD', 'JAVA', 'Advanced data structures implementation', 'flag13', 0, 100),
    (76, 'CodeReviewChallenge2', '2024-01-01', 'MEDIUM', 'C', 'Advanced code review techniques', 'flag14', 0, 70),
    (1, 'ImprovedMySQL', '2024-01-01', 'EASY', 'MYSQL', 'Solve a simple mysql problem', 'FLAG-kampeurs', 1, 47),
    (2, 'RSAMaster', '2024-01-01', 'MEDIUM','PYTHON', 'Solve a RSA problem', 'KCORPENLEC', 0, 100),
    (3, 'WEBMaster', '2024-01-01', 'HARD', 'PYTHON', 'Count the top 5 words in a web page', "[('rome', 11), ('national', 7), ('romulus', 7), ('photographie', 7), ('romains', 6)]", 0, 120),
    (4, 'ImageAnalyzer', '2024-01-01', 'EASY', 'SYSTEM', 'Find a flag hidden in a image', 'FLAG-bienjoué', 0, 20),
    (5, 'RSAMaster2', '2024-01-01', 'MEDIUM', 'PYTHON', 'Find the crypted message', '[20736,15747]', 0, 80),
    (6, 'HashMaster', '2024-01-01', 'EASY', 'PYTHON', 'Find the decrypted passowrds', '[password,123456]', 0, 30);

-- Insert completed challenges with static IDs
INSERT INTO Completed (completedUser, completedChallenge, completedStatus, completedDate)
VALUES
    (58, 63, 'STARTED', '2023-01-16'),
    (58, 64, 'STARTED', '2023-02-21'),
    (58, 65, 'STARTED', '2023-03-11'),
    (59, 63, 'STARTED', '2023-01-16'),
    (59, 64, 'STARTED', '2023-02-21'),
    (60, 63, 'STARTED', '2023-01-16'),
    (60, 64, 'COMPLETED', '2023-02-21'),
    (60, 65, 'STARTED', '2023-03-11'),
    (61, 66, 'STARTED', '2023-04-06'),
    (62, 63, 'STARTED', '2023-01-16'),
    (62, 64, 'STARTED', '2023-02-21'),
    (64, 63, 'STARTED', '2023-01-16'),
    (64, 64, 'STARTED', '2023-02-21'),
    (65, 64, 'COMPLETED', '2023-02-21'),
    (66, 64, 'STARTED', '2023-02-21'),
    (67, 63, 'STARTED', '2023-01-16'),
    (67, 64, 'STARTED', '2023-02-21');

-- Insert granted badges
INSERT INTO Granted (grantedUser, grantedBadgeRank, grantedDate)
VALUES
    (58, 1, '2023-01-20'),
    (58, 3, '2023-03-15'),
    (59, 2, '2023-02-25'),
    (60, 1, '2023-01-18'),
    (61, 2, '2023-04-10'),
    (62, 1, '2023-05-15'),
    (63, 2, '2023-06-20'),
    (64, 3, '2023-07-25'),
    (65, 3, '2023-08-30'),
    (66, 3, '2023-09-15'),
    (67, 2, '2023-10-20');

-- Instert tags
INSERT INTO Tag (tagId, tagName, tagColor)
VALUES
    (1, 'Algorithm', 'blue'),
    (2, 'Web Development', 'green'),
    (3, 'Data Structures', 'red'),
    (4, 'Code Review', 'yellow'),
    (5, 'Machine Learning', 'purple'),
    (6, 'Mobile App', 'orange'),
    (7, 'Networking', 'pink'),
    (8, 'Security', 'brown'),
    (9, 'Database', '#af4129'),
    (10, 'UI/UX', 'teal'),
    (11, 'Configuration', 'gray'),
    (12, 'Image Analysis', 'black'),
    (13, 'Cryptography', 'black'),
    (14, 'Steganography', 'purple');

-- Insert challenge tags
INSERT INTO ChallengeTag (challengeTagChallenge, challengeTagTag)
VALUES
    (1, 9),
    (1, 11),
    (2, 1),
    (3, 1),
    (4, 12),
    (5, 1),
    (5, 13),
    (6, 13),
    (6, 1),
    (63, 1),
    (63, 2),
    (63, 3),
    (64, 2),
    (64, 10),
    (65, 3),
    (65, 5),
    (66, 4),
    (67, 5),
    (67, 6),
    (68, 6),
    (69, 7),
    (70, 8),
    (71, 9),
    (72, 10),
    (73, 1),
    (74, 2),
    (75, 3),
    (76, 4),
    (2, 13),
    (3, 13),
    (4, 14);

-- Insert Achievements
INSERT INTO `Achievement` (`achievementId`, `achievementColor`, `achievementTitle`, `achievementDescription`) VALUES
(1, '#27AE60', 'Alan Turring', 'Résoudre un défi ayant un défi labellisé \"crypto\"'),
(2, '#2980B9', 'Richard Hamming', 'Résoudre un défi après (au moins) un essai raté.'),
(3, '#F1C40F', 'Ada Lovelace', 'Être le·a premier·e à résoudre un défi'),
(4, '#E67E22', 'Margaret Hamilton', 'Résoudre (au moins) deux défis en moins de 24h'),
(5, '#C0392B', 'Leslie Lamport', 'Résoudre un défi entre minuit et 6h du matin.'),
(6, '#8E44AD', 'Linus Torval', 'Se connecter depuis un OS Linux');

-- Set max_connections
SET GLOBAL max_connections = 1000000;