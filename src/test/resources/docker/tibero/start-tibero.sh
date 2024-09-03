#!/bin/sh
# 필요한 라이브러리 설치
apt-get update
apt-get install -y execstack libelf1

# libtbepl 권한 변경
execstack -c /tibero7/client/lib/libtbepl.so

cp /usr/local/license.xml /tibero7/license/
/tibero7/config/gen_tip.sh
mkdir -p /usr/bin/pstack
# 노마운트로 실행
/tibero7/bin/tbboot nomount
# sys유저로 접근, 컨트롤파일 및 유저 생성
tbsql sys/tibero << EOF
@/usr/local/sql/initial.sql
EXIT;
EOF

/tibero7/bin/tbboot

tbsql sys/tibero << EOF
@/usr/local/sql/tablespace_user_initial.sql
EXIT;
EOF
# DDL 실행
for file in /usr/local/sql/table/*.sql; do
    # echo "Executing $file" # for debug
    if [ -e "$file" ]; then
        tbsql tibero/tibero << EOF
@$file
EXIT;
EOF
    else
        echo "No .sql files found in /usr/local/sql/table/"
    fi
done

for file in /usr/local/sql/index/*.sql; do
    if [ -e "$file" ]; then
        tbsql tibero/tibero << EOF
@$file
EXIT;
EOF
    else
        echo "No .sql files found in /usr/local/sql/index/"
    fi
done

for file in /usr/local/sql/constraint/*.sql; do
    if [ -e "$file" ]; then
        tbsql tibero/tibero << EOF
@$file
EXIT;
EOF
    else
        echo "No .sql files found in /usr/local/sql/constraint/"
    fi
done

for file in /usr/local/sql/sequence/*.sql; do
    if [ -e "$file" ]; then
        tbsql tibero/tibero << EOF
@$file
EXIT;
EOF
    else
        echo "No .sql files found in /usr/local/sql/sequence/"
    fi
done

for file in /usr/local/sql/trigger/*.sql; do
    if [ -e "$file" ]; then
        tbsql tibero/tibero << EOF
@$file
EXIT;
EOF
    else
        echo "No .sql files found in /usr/local/sql/trigger/"
    fi
done

tbsql tibero/tibero << EOF
@/usr/local/sql/dml.sql
EXIT;
EOF

/tibero7/scripts/system.sh -p1 tibero -p2 syscat -a1 y -a2 y -a3 y -a4 y -sod y
tail -f /dev/null
