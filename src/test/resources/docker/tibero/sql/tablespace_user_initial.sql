create tablespace TIBERO
    datafile '/tibero7/database/tibero/tibero.tdf'
    size 2g reuse
    autoextend on next 10240k
    maxsize unlimited;

create user tibero identified by tibero default tablespace TIBERO;
grant all privileges to tibero;
