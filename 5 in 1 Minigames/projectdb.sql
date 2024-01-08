-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Dic 14, 2023 alle 22:15
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `projectdb`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `mcqs`
--

CREATE TABLE `mcqs` (
  `id` int(11) NOT NULL,
  `question` varchar(255) NOT NULL,
  `option1` varchar(255) NOT NULL,
  `option2` varchar(255) NOT NULL,
  `option3` varchar(255) NOT NULL,
  `correct` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `mcqs`
--

INSERT INTO `mcqs` (`id`, `question`, `option1`, `option2`, `option3`, `correct`) VALUES
(1, 'Which of these countries is the only one to have a non-rectangular flag?', 'Switzerland', 'Vatican City', 'Japan', 'Nepal'),
(2, 'Which of these actors has won the most Oscars for Best Actor in a Leading Role?', 'Marlon Brando', 'Tom Hanks', 'Jack Nicholson', 'Daniel Day-Lewis'),
(3, 'Which of these elements is the heaviest in terms of atomic mass?', 'Uranium', 'Plutonium', 'Hydrogen', 'Oganesson'),
(4, 'Which of these countries is not a permanent member of the United Nations Security Council?', 'China', 'France', 'Russia', 'Germany'),
(5, 'Which of these musical instruments is classified as a brass instrument?', 'Saxophone', 'Clarinet', 'Oboe', 'Trombone'),
(6, 'Which of these animals is the fastest land animal?', 'Lion', 'Ostrich', 'Pronghorn', 'Cheetah'),
(7, 'Which of these planets is the only one to rotate clockwise in the solar system?', 'Mercury', 'Earth', 'Mars', 'Venus'),
(8, 'Which of these authors wrote the classic novel “The Catcher in the Rye”?', 'Mark Twain', 'Ernest Hemmingway', 'F. Scott Fitzgerald', 'J.D. Salinger'),
(9, 'Which of these sports is played on a field with a ball and a stick?', 'Cricket', 'Baseball', 'Golf', 'Hockey'),
(10, 'Which of these colors is not one of the primary colors of pigment?', 'Cyan', 'Magenta', 'Yellow', 'Green'),
(11, 'Which of these countries is the least populous in the world?', 'Tuvalu', 'Nauru', 'Monaco', 'Vatican City'),
(12, 'Which of these artists painted the famous masterpiece “The Scream”?', 'Salvador Dali', 'Vincent Van Gogh', 'Pablo Picasso', 'Edvard Munch'),
(13, 'Which of these languages is the most widely spoken in the world?', 'English', 'Spanish', 'Hindi', 'Mandarin Chinese'),
(14, 'Which of these organs is responsible for producing bile in the human body?', 'Liver', 'Pancreas', 'Stomach', 'Gallbladder'),
(15, 'Which of these films won the Academy Award for Best Picture in 2021?', 'The Trial of The Chicago 7', 'Mank', 'The Father', 'Nomadland'),
(16, 'Which of these continents is the only one to cover all four hemispheres?', 'Asia', 'Europe', 'South America', 'Africa'),
(17, 'Which of these musical genres originated in the United States in the early 20th century?', 'Raggae', 'Rock', 'Salsa', 'Jazz'),
(18, 'Which of these rivers is the only one to flow both northward and southward across the equator?', 'Nile', 'Amazon', 'Ganges', 'Congo'),
(19, 'Which of these superheroes is not part of the DC Extended Universe?', 'Superman', 'Wonder Woman', 'Aquaman', 'Thor'),
(20, 'Which of these foods is a type of pasta?', 'Falafel', 'Sushi', 'Quiche', 'Gnocchi');

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

CREATE TABLE `users` (
  `userID` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `isNewUser` varchar(3) NOT NULL DEFAULT 'yes',
  `game1HighScore` int(11) NOT NULL DEFAULT 0,
  `game2HighScore` int(11) NOT NULL DEFAULT 0,
  `game3HighScore` int(11) NOT NULL DEFAULT 0,
  `game4HighScore` int(11) NOT NULL DEFAULT 0,
  `game5HighScore` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`userID`, `username`, `password`, `isNewUser`, `game1HighScore`, `game2HighScore`, `game3HighScore`, `game4HighScore`, `game5HighScore`) VALUES
(3, 'MoeOmar', '53ebcc6539953fb21b0629a8bbdd914f8982568f7ed1f5d742e973937e2c3dcd', 'no', 0, 0, 0, 0, 0),
(4, 'ziadSS', '245402e03069810716c368788b7cf6e18d7c7a1f8eaca128c5ec623d492c13ec', 'no', 100, 17500, 120, 60, 125),
(5, 'salahAbou', '854fb7546f75af8c4c0a8d366da4b0845fdc38f33110c33207ab02319a723882', 'no', 95, 2500, 120, 220, 0);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `mcqs`
--
ALTER TABLE `mcqs`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`userID`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `mcqs`
--
ALTER TABLE `mcqs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

--
-- AUTO_INCREMENT per la tabella `users`
--
ALTER TABLE `users`
  MODIFY `userID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
