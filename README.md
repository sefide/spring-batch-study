# spring-batch-study
spring batch study

---
###ItemReader

Spring의 JdbcTemplate는 분할 처리를 지원하지 않는다. 
Spring Batch에서는 2개의 Reader 타입을 지원한다.

1. Cursor

ResultSet이 open될 때마다 next 메서드가 호출되면서 쿼리의 결과를 반환(스트리밍 방식)
<br>
ResultSet이란 > executeQuery()로 실행된 select문으로부터 반환된 결과가 저장되는 인터페이스 <br>
cursor란 > ResultSet 객체가 가져올 수 있는 행의 위치를 지정하는 개념으로, select문 실행 시 모든 데이터를 한꺼번에 가져올 수 없기 때문에 생겨났다. <br>
next() > cursor의 위치를 이동시키는 메서드로 다음 행의 결과가 있는지 없는지 여부를 boolean값으로 반환한다. <br>
 
- JdbcCursorItemReader
- HibernateCursorItemReader
- StoredProcedureItemReader


2. Paging

페이지 단위로 데이터를 검색해오는 방식
<br>
- JdbcPagingItemReader
- HibernatePagingItemReader
- JpaPagingItemReader


---

## Test

### @SpringBatchTest

스프링 배치 기반의 테스트를 특정한다. 
아래 4가지의 빈을 자동등록한다. 

*since Spring Batch 4.1*

* JobLauncherTestUtils : Spring Batch Test 전용 유틸 제공  
* JobRepositoryTestUtils : Job Repository 관련 기능 제공 (Job Execution 테이블에 생성/제거 기능)
* JobScopeTestExecutionListener : JobScope 컨텍스트 생성 
* StepScopeTestExecutionListener : StepScope 컨텍스트 생성  

<br>
.. junit의 버전에 따라 설정 방법이 조금 다를 수 있으니 주석 참고 


