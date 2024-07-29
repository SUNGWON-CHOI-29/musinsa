# 구현 범위

### 코디서비스에 필요한 3개의 주요 API 개발
1. 카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API
```
GET localhost:8080/cody/categories/min-price
```
2. 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을
```
GET localhost:8080/cody/brands/min-pri®ce
```
3. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API
```
GET localhost:8080/cody/category/price-range
```

### 브랜드 및 상품 CRUD API
* 브랜드
```
GET localhost:8080/brands
POST localhost:8080/brands
PUT localhost:8080/brands/{brandId}/name
DELETE localhost:8080/brands/{brandId}
```
* 상품
```
GET localhost:8080/products
POST localhost:8080/products
PUT localhost:8080/products/{productId}
DELETE localhost:8080/products/{productId}
```

# 코드 빌드, 테스트, 실행 방법
* 코드 빌드
```
./gradlew build
```
* 코드 테스트
```
./gradlew test
```
* 코드 결과 html
```
open build/reports/tests/test/index.html
```

* 코드 실행방법
```
java -jar build/libs/cody-0.0.1-SNAPSHOT.jar
```

# 기타 추가 정보
* 스웨거 정보
```
http://localhost:8080/musinsa/swagger.html
```
* h2-console 정보
```
http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:db
User Name: sa
Password:
```