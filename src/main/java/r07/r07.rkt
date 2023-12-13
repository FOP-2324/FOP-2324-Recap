;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname Livecoding) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #f)))
;(list 1 2 3 4 5)
;(first (rest '(1 2 3 4 5)))
;(cons 4 (cons 5 '()))
;empty
;(define-struct person (first last age))
;(define my-list (list (make-person "Max" "Mustermann" 19) (make-person "Konrad" "Zuse" 30)))
;(map person-first (filter (lambda (x) (> (person-age x) 18)) my-list))
;(map (lambda (x) (* x x)) (list 1 2 3 4))
;(filter (lambda (x) (> x 5)) (list 3 4 5 6 7 8 9))

;(foldl - 0 '(1 2 3 4))
;(- 4 (- 3 (- 2 (- 1 0))))
;(foldr - 0 '(1 2 3 4))
;(- 1 (- 2 (- 3 (- 4 0))))
;(- 1 (- 2 (- 3 4)))


;(define (fac n)
;  (if (< n 2) 1 (* n (fac (- n 1)))))

(define (fib n)
  (cond
    [(= n 0) 0]
    [(= n 1) 1]
    [else (+ (fib (- n 1)) (fib (- n 2)))]))

(define (my-square x) (* x x))

(map (lambda (x) (* x x)) '(1 2 3 4 5))