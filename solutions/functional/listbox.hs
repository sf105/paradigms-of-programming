
module ListBoxModel(ListBoxModel(Model),arrowUp, arrowDown, pageUp, pageDown, display) where

type Size         = Int
type Selection    = Int
data Window       = Window (Int, Int) Selection deriving Show
data ListBoxModel = Model Size Window deriving Show

onPair :: (a -> b) -> (a,a) -> (b,b)
onPair op (x, y) = (op x, op y)

flipPair :: (a,b) -> (b,a)
flipPair (x,y) = (y,x)

windowSize :: Window -> Int
windowSize (Window (top, bottom) _) = (bottom - top) + 1

reflectWindow :: Int -> Window -> Window
reflectWindow about (Window range selection) = Window (invertPair range) (inv selection)
    where invertPair = onPair inv . flipPair
          inv x      = (about - 1) - x

selectionAtEdge :: Window -> Bool
selectionAtEdge (Window (top, _) selection) = top == selection

windowAtEdge :: Window -> Bool
windowAtEdge (Window (top, _) _) = top == 0

shiftSelection :: Window -> Window
shiftSelection (Window size selection) = Window size (selection - 1)

shiftWindow :: Window -> Window
shiftWindow (Window range selection) = Window (onPair s1 range) (s1 selection)
    where s1 = subtract 1 

selectTop :: ListBoxModel -> ListBoxModel
selectTop (Model size (Window (top, bottom) _)) = Model size (Window (top, bottom) top)

reflect :: ListBoxModel -> ListBoxModel
reflect (Model size w) = Model size (reflectWindow size w)

arrowUp :: ListBoxModel -> ListBoxModel
arrowUp m@(Model size w)
    | not $ selectionAtEdge w   = Model size (shiftSelection w)
    | windowAtEdge w            = m
    | otherwise                 = Model size (shiftWindow w)

arrowDown :: ListBoxModel -> ListBoxModel 
arrowDown = reflect . arrowUp . reflect

pageWith :: (ListBoxModel -> ListBoxModel) -> ListBoxModel -> ListBoxModel
pageWith step m@(Model _ w) = selectTop $ (iterate step (selectTop m)) !! windowSize w

pageUp :: ListBoxModel -> ListBoxModel
pageUp = pageWith arrowUp 

pageDown :: ListBoxModel -> ListBoxModel
pageDown = pageWith arrowDown

display :: [a] -> ListBoxModel -> ([a], a)
display totalList (Model _ w@(Window (top, _) selection)) = 
    (displayList, selectedElement) 
        where displayList      = (take size . drop top) totalList
              selectedElement  = totalList !! selection        
              size             = windowSize w 

