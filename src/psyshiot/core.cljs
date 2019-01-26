(ns psyshiot.core
    (:require ))

(enable-console-print!)

(def s0 "가각갛까나a1힣")
(def ga (.charCodeAt "가" 0))
(def hih (.charCodeAt "힣" 0))
(def kadokawa "카도카와")

(defn split-cjj0 [code]
  (let [
        c (- code 0xAC00)
        jong (mod c 28)
        a (int (/ c 28))
        jung (mod a 21)
        cho (int (/ a 21))
        ]
    [cho jung jong]
    ))

(defn split-cjj [ch]
  (let [code (.charCodeAt ch 0)]
    (if (or (< code 0xAC00) (> code 0xD7A3))
      ch
      (split-cjj0 code)
      )))
(defn join-cjj [ch]
  (if (string? ch)
    ch
    (let [[cho jung jong] ch]
      (String/fromCharCode
        (+ 0xAC00
          jong
          (* 28 jung)
          (* 28 21 cho)
          )))))

(defn add-18 [ch]
  (if (and (vector? ch) (== 0 (last ch)))
    [(first ch) (second ch) 18]
    ch))

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
