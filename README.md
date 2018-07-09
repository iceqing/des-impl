# Java 实现 DES 加密解密

>本文使用 Java 来实现 DES 加密解密。仅作为学习用途，用于理解密码的基础理论

DES 加密共分为 几个步骤
1、IP置换
2、密钥置换
3、E扩展置换
4、S盒代替
5、P盒置换
6、IP-1末置换

DES 算法有工作模式[1]
1、ECB
2、CBC
3、CFB
4、CTF

填充模式实现了ZeroPadding

本文实现了前两种工作模式。

## 参考资料：
[1] 密码学实践，Niels Ferguson, Bruce Schneier，张振锋，徐静，李红达等译，电子工业出版社