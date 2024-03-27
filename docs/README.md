# API docs

# Swagger

[http://49.50.166.191:8080/swagger-ui/index.html#/](http://49.50.166.191:8080/swagger-ui/index.html#/)

# 질문 게시판

현재 이미지 등록은 구현이 안되어있기 때문에 request 시 제외하고 swagger 통해 요청 날려볼 수 있습니다.
응답값이나 예외처리는 아직 다 안된상태라 간단한 응답만 반환

## /api/posts
- 질문 등록  
  `POST {{baseUrl}}:8080/api/posts`

    ```json
    // request body
    {
        "id": 1,
        "title": "title",  // 필수값
        "content":	"this is content",  // 필수값
        "images":	[
            {"id": "imageId", "url": "imageUrl"}
        ],
        "password":	1234
    }
    ```

    - `image` 의 경우 아직 구현 안됨
    - 등록된 id값 반환
- 질문 삭제  
  `DELETE {{baseUrl}}:8080/api/posts/{postId}`

    ```json
    // request body
    {
        "password": 1234,
        "title": "title", // 필수값
        "content": "content", // 필수값
    }
    ```

    - `password`가 DB에 저장된 것과 일치하면 삭제되고 `true` 반환, 아니면 `false` 반환

- 질문 수정  
  `PATCH {{baseUrl}}:8080/api/posts/{postId}`

    ```json
    // request body
    {
        "title": "제목 수정", // 필수값
        "content": "내용 수정", // 필수값
        "images": [
            {"id": "imageId", "url": "imageUrl"}
        ]
    }
    ```

    - 제목, 내용, 이미지 수정 가능 → 이미지 아직 구현 안됨
    - 수정된 게시글 ID 반환
- 질문 조회  
  `GET {{baseUrl}}:8080/api/posts/{postId}`
    - `postId`로 게시글 조회

    ```json
    // response
    {
        "postId": 5,
        "title": "string",
        "content": "string",
        "images" -> 추가 예정
    }
    ```

- 리스트 조회  
`GET {{baseUrl}}:8080/api/posts`
    - 전체 게시글 리스트 조회
    - 현재는 페이지네이션으로 구현 → 무한 스크롤로 교체 예정

        ```json
        // response
        {
            "content": [
                {
                    "postId": 2,
                    "title": "the second title",
                    "content": "a short sentence2"
                },
                {
                    "postId": 1,
                    "title": "제목 수정",
                    "content": "내용 수정"
                },
                {
                    "postId": 5,
                    "title": "string",
                    "content": "string"
                }
            ],
            "pageable": {
                "pageNumber": 0,
                "pageSize": 10,
                "sort": {
                    "empty": true,
                    "sorted": false,
                    "unsorted": true
                },
                "offset": 0,
                "paged": true,
                "unpaged": false
            },
            "last": true,
            "totalPages": 1,
            "totalElements": 3,
            "size": 10,
            "first": true,
            "number": 0,
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "numberOfElements": 3,
            "empty": false
        }
        ```

## /api/comments
- 댓글 등록  
      `POST {{baseUrl}}:8080/api/comments`

    ```json
    // request body
    {
        "content": "test. comment33",
        "postId": 2
    }
    ```

    ```json
    // response
    {
        "content": "test. comment33",
        "postId": 2
    }
    ```
  댓글 내용과 댓글이 달릴 `postId`가 필수값

    - 댓글 조회  
      `GET {{baseUrl}}:8080/api/comments/{postId}`
    ```json
    // response
    {
        "content": [
            {
                "content": "test. comment33",
                "postId": 2
            },
            {
                "content": "test. comment33",
                "postId": 2
            }
        ],
        "pageable": {
            "pageNumber": 0,
            "pageSize": 5,
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 0,
            "paged": true,
            "unpaged": false
        },
        "last": true,
        "totalPages": 1,
        "totalElements": 2,
        "size": 5,
        "number": 0,
        "first": true,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "numberOfElements": 2,
        "empty": false
    }
    ```
  댓글 역시 pagenation으로 리턴

## /api/images
## 메인 화면