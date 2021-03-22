# Spring Batch Deploy & Build

Execute Shell
```
java -jar -XX:+UseG2GC -Dspring.profiles.active=real 
배치jar --job.name=${JobName} 
parameter1=${parameter1} parameter2=parameter2
```
=> GC 크기를 2G로 설정

=> 잡 파라미터를 전달하는 경우 parameter1과 같이 직접 값을 넣어주는 경우 parameter2와 같이 설정하면 된다.  



#### jenkins plugin

- Naginator : job 빌드 실패 시, 재시도할 수 있게끔 스켸쥴링

참고 : https://plugins.jenkins.io/naginator/
