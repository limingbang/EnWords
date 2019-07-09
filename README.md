# EnWords
模仿百词斩的部分功能，也加入了一些新的想法，针对英语四级词汇的App。
# 效果
![image](https://github.com/limingbang/EnWords/blob/master/app.gif)
# 简介
这是学年论文的作品，目前主要实现了登录模块、注册模块、主界面模块、单词学习模块、听力训练模块五个功能模块。以Material Design的组件为主搭建了UI界面，布局上以ConstraintLayout为主，使用OkHttp来请求服务器资源。登录、注册、听力训练三个模块使用了MVP的开发模式。另外，也实现了打卡的功能。如果打卡成功，主界面的日历控件对应日期的背景会变成蓝色，可以通过打卡的日期复习该天的单词。
# 说明
- EnWords是app的工程文件
- Server存放了后台（JavaWeb）的源码以及MySQL的数据库文件。后台使用的开发工具是Intellij IDEA，数据库的版本是5.7
- AllResource存放了测试所用的资源，其中article是听力训练模块用的资料，resource是单词学习模块用的资料，测试是它们都放在本地的Apache
# 开源库及参考资料
- [OkHttp](https://github.com/square/okhttp)
- [CircleImageView](https://github.com/hdodenhof/CircleImageView)
- [对话框工具](https://github.com/kongzue/Dialog)
- [Picasso](https://github.com/square/picasso)
- [打卡日历](https://github.com/HzwSunshine/SignCalendarProgect)
- [个人页面的实现](https://blog.csdn.net/ASFang/article/details/80813247)
- [Material Design](https://material.io/)
# 不足
- 在请求资源时，有时会出现延时
- 对MediaPlayer封装实现的播放器不完善
- 一些模块还没实现
- 在网络出现延时时，处理不完善
# 展望
由于能力的限制，在设计和编码上存在一定问题，希望通过不断的学习去完善app的功能， 以及修复bug。
