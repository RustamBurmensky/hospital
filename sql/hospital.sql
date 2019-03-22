-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Мар 22 2019 г., 00:03
-- Версия сервера: 5.7.23
-- Версия PHP: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `hospital`
--

-- --------------------------------------------------------

--
-- Структура таблицы `appointments`
--

CREATE TABLE `appointments` (
  `user_id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `appointments`
--

INSERT INTO `appointments` (`user_id`, `patient_id`) VALUES
(11, 1),
(12, 2),
(12, 3),
(12, 4);

-- --------------------------------------------------------

--
-- Структура таблицы `discharges`
--

CREATE TABLE `discharges` (
  `id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `discharges`
--

INSERT INTO `discharges` (`id`, `patient_id`, `date`) VALUES
(2, 2, '2019-03-16');

-- --------------------------------------------------------

--
-- Структура таблицы `discharges_description`
--

CREATE TABLE `discharges_description` (
  `discharge_id` int(11) NOT NULL,
  `lang_id` tinyint(4) NOT NULL,
  `diagnosis` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `discharges_description`
--

INSERT INTO `discharges_description` (`discharge_id`, `lang_id`, `diagnosis`) VALUES
(2, 1, 'Hospital discharge test.'),
(2, 2, 'Тест виписки із лікарні.'),
(2, 3, 'Тест выписки из больницы.');

-- --------------------------------------------------------

--
-- Структура таблицы `health_card_records`
--

CREATE TABLE `health_card_records` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `patient_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `record_type_id` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `health_card_records`
--

INSERT INTO `health_card_records` (`id`, `user_id`, `patient_id`, `date`, `record_type_id`) VALUES
(1, 1, 2, '2019-03-09', 1),
(3, 13, 2, '2019-03-17', 2),
(5, 12, 2, '2019-03-18', 3);

-- --------------------------------------------------------

--
-- Структура таблицы `health_card_records_description`
--

CREATE TABLE `health_card_records_description` (
  `record_id` int(11) NOT NULL,
  `lang_id` tinyint(4) NOT NULL,
  `text` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `health_card_records_description`
--

INSERT INTO `health_card_records_description` (`record_id`, `lang_id`, `text`) VALUES
(1, 1, 'Cold'),
(1, 2, 'Застуда'),
(1, 3, 'Текст. Текст. Текст. Текст. Текст. Текст. Текст. Текст.'),
(3, 1, 'Ophtalmotek'),
(3, 2, 'Офтальмотек'),
(3, 3, 'Офтальмотек'),
(5, 1, 'Procedure.'),
(5, 2, 'Процедура.'),
(5, 3, 'Процедура.');

-- --------------------------------------------------------

--
-- Структура таблицы `patients`
--

CREATE TABLE `patients` (
  `id` int(11) NOT NULL,
  `birthday` date NOT NULL,
  `weight` smallint(5) UNSIGNED NOT NULL,
  `height` smallint(5) UNSIGNED NOT NULL,
  `admission_date` date NOT NULL,
  `inpatient` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `patients`
--

INSERT INTO `patients` (`id`, `birthday`, `weight`, `height`, `admission_date`, `inpatient`) VALUES
(1, '1987-08-19', 78, 182, '2019-03-10', 0),
(2, '1991-11-12', 68, 175, '2019-03-12', 1),
(3, '1978-12-11', 84, 183, '2019-03-13', 0),
(4, '1965-02-17', 65, 182, '2019-03-15', 0);

-- --------------------------------------------------------

--
-- Структура таблицы `patients_description`
--

CREATE TABLE `patients_description` (
  `patient_id` int(11) NOT NULL,
  `lang_id` tinyint(4) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `second_name` varchar(255) NOT NULL,
  `patronymic` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `occupation` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `patients_description`
--

INSERT INTO `patients_description` (`patient_id`, `lang_id`, `first_name`, `second_name`, `patronymic`, `address`, `occupation`) VALUES
(1, 1, 'Dmitriy', 'Rybak', 'Valerievich', 'Heroiv Pratsi Str., 12, ap. 6', 'System administrator at Vegas'),
(1, 2, 'Дмитро', 'Рибак', 'Валерійович', 'вул. Героїв праці, 12, кв. 6', 'Системний адміністратор в Vegas'),
(1, 3, 'Дмитрий', 'Рыбак', 'Валерьевич', 'ул. Героев труда, 12, кв. 6', 'Системный администратор в Vegas'),
(2, 1, 'Sergey', 'Volkov', 'Igorevich', 'Sadovaya Str., 23, ap. 5', 'Manager at pizzeria'),
(2, 2, 'Сергій', 'Волков', 'Ігоревич', 'вул. Садова, 23, кв. 5', 'Менеджер в піцерії'),
(2, 3, 'Сергей', 'Волков', 'Игоревич', 'ул. Садовая, 23, кв. 5', 'Менеджер в пиццерии'),
(3, 1, 'Vasiliy', 'Zubkov', 'Olegovich', 'Gagarina Ave., 54, ap. 228', 'Seller at Silpo'),
(3, 2, 'Василь', 'Зубков', 'Олегович', 'пр. Гагаріна., 54, кв. 228', 'Продавець в Сільпо'),
(3, 3, 'Василий', 'Зубков', 'Олегович', 'пр. Гагарина., 54, кв. 228', 'Продавец в Сильпо'),
(4, 1, 'Boris', 'Smirnov', 'Gennadievich', 'Kotsarska Str., 18, ap. 12', 'Professor at KhAI'),
(4, 2, 'Борис', 'Смирнов', 'Геннадійович', 'вул. Коцарська., 18, кв. 12', 'Викладач в ХАІ'),
(4, 3, 'Борис', 'Смирнов', 'Геннадиевич', 'ул. Коцарская., 18, кв. 12', 'Преподаватель в ХАИ');

-- --------------------------------------------------------

--
-- Структура таблицы `specializations`
--

CREATE TABLE `specializations` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `specializations`
--

INSERT INTO `specializations` (`id`) VALUES
(1),
(2),
(6),
(7),
(8),
(9);

-- --------------------------------------------------------

--
-- Структура таблицы `specializations_description`
--

CREATE TABLE `specializations_description` (
  `specialization_id` int(11) NOT NULL,
  `lang_id` tinyint(4) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `specializations_description`
--

INSERT INTO `specializations_description` (`specialization_id`, `lang_id`, `name`) VALUES
(1, 1, 'Administrator'),
(1, 2, 'Адміністратор'),
(1, 3, 'Администратор'),
(2, 1, 'Surgeon'),
(2, 2, 'Хірург'),
(2, 3, 'Хирург'),
(6, 1, 'Ophthalmologist'),
(6, 2, 'Офтальмолог'),
(6, 3, 'Офтальмолог'),
(7, 1, 'Otolaryngologist'),
(7, 2, 'Отоларинголог'),
(7, 3, 'Отоларинголог'),
(8, 1, 'Pulmonologist'),
(8, 2, 'Пульмонолог'),
(8, 3, 'Пульмонолог'),
(9, 1, 'Allergologist'),
(9, 2, 'Алерголог'),
(9, 3, 'Аллерголог');

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `birthday` date NOT NULL,
  `role_id` tinyint(4) NOT NULL,
  `specialization_id` int(11) NOT NULL,
  `lang_id` tinyint(4) NOT NULL,
  `login` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id`, `birthday`, `role_id`, `specialization_id`, `lang_id`, `login`, `password`) VALUES
(1, '1996-04-26', 1, 1, 3, 'admin', 'admin'),
(11, '1971-02-19', 2, 2, 3, 'zinchenko_sergey', '12345'),
(12, '1970-05-10', 2, 6, 3, 'nosenko_vitaliy', '12345'),
(13, '1982-05-16', 3, 2, 3, 'zhanna_leonidovna', '12345');

-- --------------------------------------------------------

--
-- Структура таблицы `users_description`
--

CREATE TABLE `users_description` (
  `user_id` int(11) NOT NULL,
  `lang_id` tinyint(4) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `second_name` varchar(255) NOT NULL,
  `patronymic` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `users_description`
--

INSERT INTO `users_description` (`user_id`, `lang_id`, `first_name`, `second_name`, `patronymic`) VALUES
(1, 1, 'Admin', 'Adminenko', 'Adminovich'),
(1, 2, 'Адмін', 'Адміненко', 'Адмінович'),
(1, 3, 'Админ', 'Админенко', 'Админович'),
(11, 1, 'Sergey', 'Zinchenko', 'Ivanovich'),
(11, 2, 'Сергій', 'Зінченко', 'Іванович'),
(11, 3, 'Сергей', 'Зинченко', 'Иванович'),
(12, 1, 'Evgeniy', 'Nosenko', 'Vitaliyevich'),
(12, 2, 'Євген', 'Носенко', 'Віталійович'),
(12, 3, 'Евгений', 'Носенко', 'Витальевич'),
(13, 1, 'Zhanna', 'Mironova', 'Leonidovna'),
(13, 2, 'Жанна', 'Миронова', 'Леонидівна'),
(13, 3, 'Жанна', 'Миронова', 'Леонидовна');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `appointments`
--
ALTER TABLE `appointments`
  ADD PRIMARY KEY (`user_id`,`patient_id`),
  ADD KEY `appointments_ibfk_1` (`patient_id`);

--
-- Индексы таблицы `discharges`
--
ALTER TABLE `discharges`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `patient_id` (`patient_id`);

--
-- Индексы таблицы `discharges_description`
--
ALTER TABLE `discharges_description`
  ADD PRIMARY KEY (`discharge_id`,`lang_id`);

--
-- Индексы таблицы `health_card_records`
--
ALTER TABLE `health_card_records`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `patient_id` (`patient_id`);

--
-- Индексы таблицы `health_card_records_description`
--
ALTER TABLE `health_card_records_description`
  ADD PRIMARY KEY (`record_id`,`lang_id`);

--
-- Индексы таблицы `patients`
--
ALTER TABLE `patients`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `patients_description`
--
ALTER TABLE `patients_description`
  ADD PRIMARY KEY (`patient_id`,`lang_id`);

--
-- Индексы таблицы `specializations`
--
ALTER TABLE `specializations`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `specializations_description`
--
ALTER TABLE `specializations_description`
  ADD PRIMARY KEY (`specialization_id`,`lang_id`);

--
-- Индексы таблицы `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `login` (`login`),
  ADD KEY `specialization_id` (`specialization_id`);

--
-- Индексы таблицы `users_description`
--
ALTER TABLE `users_description`
  ADD PRIMARY KEY (`user_id`,`lang_id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `discharges`
--
ALTER TABLE `discharges`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT для таблицы `health_card_records`
--
ALTER TABLE `health_card_records`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT для таблицы `patients`
--
ALTER TABLE `patients`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `specializations`
--
ALTER TABLE `specializations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT для таблицы `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `appointments`
--
ALTER TABLE `appointments`
  ADD CONSTRAINT `appointments_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `appointments_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Ограничения внешнего ключа таблицы `discharges`
--
ALTER TABLE `discharges`
  ADD CONSTRAINT `discharges_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`) ON DELETE CASCADE;

--
-- Ограничения внешнего ключа таблицы `discharges_description`
--
ALTER TABLE `discharges_description`
  ADD CONSTRAINT `discharges_description_ibfk_1` FOREIGN KEY (`discharge_id`) REFERENCES `discharges` (`id`) ON DELETE CASCADE;

--
-- Ограничения внешнего ключа таблицы `health_card_records`
--
ALTER TABLE `health_card_records`
  ADD CONSTRAINT `health_card_records_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `health_card_records_ibfk_2` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`);

--
-- Ограничения внешнего ключа таблицы `health_card_records_description`
--
ALTER TABLE `health_card_records_description`
  ADD CONSTRAINT `health_card_records_description_ibfk_1` FOREIGN KEY (`record_id`) REFERENCES `health_card_records` (`id`) ON DELETE CASCADE;

--
-- Ограничения внешнего ключа таблицы `patients_description`
--
ALTER TABLE `patients_description`
  ADD CONSTRAINT `patients_description_ibfk_1` FOREIGN KEY (`patient_id`) REFERENCES `patients` (`id`) ON DELETE CASCADE;

--
-- Ограничения внешнего ключа таблицы `specializations_description`
--
ALTER TABLE `specializations_description`
  ADD CONSTRAINT `specializations_description_ibfk_1` FOREIGN KEY (`specialization_id`) REFERENCES `specializations` (`id`) ON DELETE CASCADE;

--
-- Ограничения внешнего ключа таблицы `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`specialization_id`) REFERENCES `specializations` (`id`);

--
-- Ограничения внешнего ключа таблицы `users_description`
--
ALTER TABLE `users_description`
  ADD CONSTRAINT `users_description_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
