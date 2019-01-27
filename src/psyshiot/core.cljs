(ns psyshiot.core
    (:require
      [clojure.string :as str]
      ))

(enable-console-print!)

(def jQ js/jQuery)

(def ta-from (jQ "#ta_from"))
(def ta-to (jQ "#ta_to"))
(def convert-btn (jQ "#convertBtn"))
(def force-jong (jQ "#force_jong"))

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
  (if (and (vector? ch))
    (let [[c ju jo] ch]
      (if (== 0 jo)
        [c ju 18]
        [c ju jo]))
    ch))

(defn add-18-all [ch]
  (if (and (vector? ch))
    (let [[c ju _] ch]
        [c ju 18])
    ch))


(defn init-core []

  (-> convert-btn
    (.off)
    (.on "click"
      (fn []
        (let [add-f (if (.prop force-jong "checked") add-18-all add-18)]
        (-> ta-to
          (.val
            (str/join
              (map (comp join-cjj add-f split-cjj)
                (-> ta-from .val)))
            ))))
        nil))
  )


(init-core)


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
