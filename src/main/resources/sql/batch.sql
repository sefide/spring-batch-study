use spring_batch;

## Job 실행 기록 - 고유한 JobParamter만 기록
SELECT * FROM BATCH_JOB_INSTANCE;

## Job 실행(JOB_INSTANCE)의 성공/실패 내역 기록
SELECT * FROM BATCH_JOB_EXECUTION;

## JOB_EXECUTION에 따른 Job Parameter 기록
SELECT * FROM BATCH_JOB_EXECUTION_PARAMS;

## Job내에 위치한 Step 실행 기록
SELECT * FROM BATCH_STEP_EXECUTION;