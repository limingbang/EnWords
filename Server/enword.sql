-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: 2019-06-28 14:30:24
-- 服务器版本： 5.7.17-log
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `enword`
--

-- --------------------------------------------------------

--
-- 表的结构 `article_path`
--

CREATE TABLE `article_path` (
  `id` int(11) NOT NULL,
  `path` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `article_path`
--

INSERT INTO `article_path` (`id`, `path`) VALUES
(1, 'read_1'),
(2, 'read_2'),
(3, 'read_3');

-- --------------------------------------------------------

--
-- 表的结构 `res_path`
--

CREATE TABLE `res_path` (
  `id` int(11) NOT NULL,
  `path` varchar(45) NOT NULL DEFAULT '资源路径'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `res_path`
--

INSERT INTO `res_path` (`id`, `path`) VALUES
(1, 'zp_1'),
(2, 'zp_2'),
(3, 'zp_3'),
(4, 'zp_4'),
(5, 'zp_5'),
(6, 'zp_6'),
(7, 'zp_7'),
(8, 'zp_8'),
(9, 'zp_9'),
(10, 'zp_10'),
(11, 'zp_11'),
(12, 'zp_12'),
(13, 'zp_13'),
(14, 'zp_14'),
(15, 'zp_15'),
(16, 'zp_16'),
(17, 'zp_17'),
(18, 'zp_18'),
(19, 'zp_19'),
(20, 'zp_20'),
(21, 'zp_21'),
(22, 'zp_22'),
(23, 'zp_23'),
(24, 'zp_24'),
(25, 'zp_25'),
(26, 'zp_26');

-- --------------------------------------------------------

--
-- 表的结构 `res_user`
--

CREATE TABLE `res_user` (
  `id` int(11) NOT NULL,
  `resId` int(11) NOT NULL,
  `userId` int(11) NOT NULL,
  `date` varchar(45) DEFAULT '打卡日期'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `res_user`
--

INSERT INTO `res_user` (`id`, `resId`, `userId`, `date`) VALUES
(109, 7, 1, '2019-06-16'),
(110, 8, 1, '2019-06-16'),
(111, 9, 1, '2019-06-16'),
(112, 10, 1, '2019-06-16'),
(113, 11, 1, '2019-06-16'),
(114, 12, 1, '2019-06-16'),
(115, 13, 1, '2019-06-16'),
(116, 14, 1, '2019-06-16'),
(152, 1, 4, '2019-06-26'),
(153, 2, 4, '2019-06-26'),
(154, 3, 4, '2019-06-26'),
(155, 4, 4, '2019-06-26'),
(156, 5, 4, '2019-06-26'),
(157, 15, 1, '2019-06-27'),
(158, 16, 1, '2019-06-27'),
(159, 17, 1, '2019-06-27'),
(160, 18, 1, '2019-06-27'),
(161, 19, 1, '2019-06-28'),
(162, 20, 1, '2019-06-28'),
(163, 21, 1, '2019-06-28'),
(164, 22, 1, '2019-06-28');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `name` char(11) NOT NULL DEFAULT '账号（手机号）',
  `password` varchar(45) NOT NULL DEFAULT '密码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`id`, `name`, `password`) VALUES
(1, '13077581227', '123456'),
(2, '13421784175', '123456'),
(3, '12345678901', '123456'),
(4, '11111111111', '123456');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `article_path`
--
ALTER TABLE `article_path`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `res_path`
--
ALTER TABLE `res_path`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `res_user`
--
ALTER TABLE `res_user`
  ADD PRIMARY KEY (`id`),
  ADD KEY `resId_idx` (`resId`),
  ADD KEY `userId_idx` (`userId`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `article_path`
--
ALTER TABLE `article_path`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- 使用表AUTO_INCREMENT `res_path`
--
ALTER TABLE `res_path`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
--
-- 使用表AUTO_INCREMENT `res_user`
--
ALTER TABLE `res_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=165;
--
-- 使用表AUTO_INCREMENT `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- 限制导出的表
--

--
-- 限制表 `res_user`
--
ALTER TABLE `res_user`
  ADD CONSTRAINT `resId` FOREIGN KEY (`resId`) REFERENCES `res_path` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `userId` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
