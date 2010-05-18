
type Point = (Double, Double)

sq :: Double -> Double
sq x = x ^ 2

translate :: Point -> Point -> Point
translate (r0, i0) (r1, i1) = 
  (r0 + r1, i0 + i1)

mandel :: Point -> Point
mandel (r, i) = 
  (sq r + sq i, 2 * r * i)

notEscaped :: Point -> Bool
notEscaped (r, i) =
  (sq r + sq i) <= 4.0

trajectory :: (Point -> Point) -> [Point]
trajectory pointFunction =
  takeWhile notEscaped $ iterate pointFunction seed
    where seed = (0.0, 0.0)

escapeIterations :: (Point -> Point) -> Int
escapeIterations =
  length . tail . take 1024 . trajectory

mandelbrot :: Double -> [[Int]]
mandelbrot incrementSize =
  [[ escapeIterations $ translate (x, y) . mandel
    | x <- increments]
    | y <- increments] where
        increments = [0.0, incrementSize .. ]

window :: (Int, Int) -> (Int, Int) -> [[a]] -> [[a]]
window (x0, y0) (x1, y1) = range y0 y1 . map (range x0 x1) where
  range m n = take (n - m) . drop m

