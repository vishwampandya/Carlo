-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Sep 15, 2021 at 12:56 PM
-- Server version: 8.0.18
-- PHP Version: 7.3.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `carlo`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `bookID` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `carID` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `userID` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `ownerID` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `price` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `pickUP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `drop_car` timestamp NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `car`
--

CREATE TABLE `car` (
  `carID` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `car_model` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `car_company` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `ownerID` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `status` varchar(10) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'available',
  `description` text COLLATE utf8mb4_general_ci NOT NULL,
  `photoparth` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `perkmprice` double NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `car`
--

INSERT INTO `car` (`carID`, `car_model`, `car_company`, `ownerID`, `status`, `description`, `photoparth`, `perkmprice`, `timestamp`) VALUES
('5e61e626cb8e6', 'Hyundai ', 'I 20', '5e5c003d5b15c', 'available', 'The Hyundai Elite i20 has 1 Diesel Engine and 1 Petrol Engine on offer. The Diesel engine is 1396 cc while the Petrol engine is 1197 cc. It is available with the Manual and Automatic transmission. Depending upon the variant and fuel type the Elite i20 has a mileage of 17.4 to 22.54 kmpl. The Elite i20 is a 5 seater Hatchback and has a length of 3985mm, width of 1734mm and a wheelbase of 2570mm.', '1583474215214', 30, '2020-03-06 05:56:54'),
('5e61e684b030d', 'Sport Rover', 'Range Rover', '5e5c003d5b15c', 'available', 'The Land Rover Range Rover Evoque has 1 Diesel Engine and 1 Petrol Engine on offer. The Diesel engine is 1999 cc while the Petrol engine is 1997 cc. It is available with the Automatic transmission. The Range Rover Evoque is a 5 seater SUV and has a length of 4371 mm, width of 1996 mm and a wheelbase of 2681mm.', '1583474309094', 100, '2020-03-06 05:58:28');

-- --------------------------------------------------------

--
-- Table structure for table `chat`
--

CREATE TABLE `chat` (
  `chatID` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `senderId` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `reciverID` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `message` text COLLATE utf8mb4_general_ci NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `feedback`
--

CREATE TABLE `feedback` (
  `feedbackID` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `message` text COLLATE utf8mb4_general_ci NOT NULL,
  `userID` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `feedback`
--

INSERT INTO `feedback` (`feedbackID`, `message`, `userID`, `timestamp`) VALUES
('5e5ecb8c20168', 'great app love this app\n', '5e5bea5656531', '2020-03-03 21:26:36'),
('5e5ece3d6fc78', 'I love this app', '5e5c003d5b15c', '2020-03-03 21:38:05'),
('5e5f59f2e565d', 'hug7g 7g7g 8g7g b8h8', '5e5f596c34a5b', '2020-03-04 07:34:10');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `paymentID` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `amount` double NOT NULL,
  `bookID` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `registration`
--

CREATE TABLE `registration` (
  `regID` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `Name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `address` text COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(10) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'user',
  `ph` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `password` text COLLATE utf8mb4_general_ci NOT NULL,
  `gender` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPIID` varchar(50) COLLATE utf8mb4_general_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `registration`
--

INSERT INTO `registration` (`regID`, `Name`, `address`, `role`, `ph`, `email`, `password`, `gender`, `timestamp`, `UPIID`) VALUES
('5e5bea5656531', 'ravi', 'jay ambae society', 'user', '7383387646', 'ravi@gmail.com', 'joshi@123', 'Male', '2020-03-01 17:01:10', '7383387646@apl'),
('5e5c003d5b15c', 'thotho', 'jay ambey', 'owner', '9974054836', 'thotho@gmail.com', 'thotho', 'Female', '2020-03-01 18:34:37', '8866333296@apl'),
('5e5f596c34a5b', 'RUTVIK', '49,someshwar Society. ', 'user', '9913718096', 'kathiriyarutu1213@gmail.com ', '1213', 'Male', '2020-03-04 07:31:56', '12'),
('5e5f5a5533d1f', 'zeel', 'krishanadham', 'owner', '8264728727', 'zeel8264@gmail.com', '8264', 'Male', '2020-03-04 07:35:49', '0101');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`bookID`),
  ADD KEY `carID` (`carID`),
  ADD KEY `ownerID` (`ownerID`),
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `car`
--
ALTER TABLE `car`
  ADD PRIMARY KEY (`carID`),
  ADD KEY `ownerID` (`ownerID`);

--
-- Indexes for table `chat`
--
ALTER TABLE `chat`
  ADD PRIMARY KEY (`chatID`),
  ADD KEY `reciverID` (`reciverID`),
  ADD KEY `senderId` (`senderId`);

--
-- Indexes for table `feedback`
--
ALTER TABLE `feedback`
  ADD PRIMARY KEY (`feedbackID`),
  ADD KEY `userID` (`userID`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`paymentID`),
  ADD UNIQUE KEY `bookID` (`bookID`);

--
-- Indexes for table `registration`
--
ALTER TABLE `registration`
  ADD PRIMARY KEY (`regID`),
  ADD UNIQUE KEY `ph` (`ph`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`carID`) REFERENCES `car` (`carID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`ownerID`) REFERENCES `registration` (`regID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `booking_ibfk_3` FOREIGN KEY (`userID`) REFERENCES `registration` (`regID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `car`
--
ALTER TABLE `car`
  ADD CONSTRAINT `car_ibfk_1` FOREIGN KEY (`ownerID`) REFERENCES `registration` (`regID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `chat`
--
ALTER TABLE `chat`
  ADD CONSTRAINT `chat_ibfk_1` FOREIGN KEY (`reciverID`) REFERENCES `registration` (`regID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `chat_ibfk_2` FOREIGN KEY (`senderId`) REFERENCES `registration` (`regID`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `feedback`
--
ALTER TABLE `feedback`
  ADD CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `registration` (`regID`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
