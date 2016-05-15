doubleClick("1443672748520.png")  
if exists("1443762926642.png"):
   click("1443672796845.png")
wait("1443673909139.png",180)
type("1443680219110.png","Ssk1")
type("1443680266321.png","cambio123")
click("1443674183523.png")
wait("1443674030735.png",20)
click("1443674047855.png")
if exists("1443676701476.png"):
    click("1443676701476.png")
wait("1443676969055.png",180)
type("1443677380768.png", Key.BACKSPACE + Key.BACKSPACE + "197902199285") 
click("1443677150572.png")
click("1443679284780.png") 
hover("1444036055842.png")
hover("1444041154992.png")
click("1444040416778.png")
wait(Pattern("1444105631848.png").targetOffset(2,-14),10)
click(Pattern("1444105631848.png").targetOffset(2,-14))
while not exists(Pattern("1444107242456.png").exact().targetOffset(3,15))and not exists(Pattern("1444107698352.png").exact()):    
    dragDrop(Pattern("1444112341753.png").targetOffset(-1,-5), Pattern("1444112345012.png").targetOffset(11,-7))     
if  exists(Pattern("1444107242456.png").exact().targetOffset(3,15)):    
    rightClick(Pattern("1444107242456.png").exact().targetOffset(3,15))
else:
    if exists(Pattern("1444107698352.png").exact()):
        rightClick(Pattern("1444107698352.png").exact())
click(Pattern("1444111689293.png").targetOffset(-2,-14))
wait(5) 
if exists("1444044224508.png"):
    type("1444044240397.png", "automated justification")
    click("1444044266357.png")
wait(5)
if exists("1444044280502.png"):
    type(Pattern("1444044345410.png").targetOffset(59,0), "automated dispense comment")
    click(Pattern("1444111770010.png").targetOffset(44,-2))
while not  exists(Pattern("1444110337152.png").exact())and not exists(Pattern("1444110353380.png").exact()):    
    dragDrop(Pattern("1444112341753.png").targetOffset(-1,-5), Pattern("1444112345012.png").targetOffset(11,-7))          
if exists(Pattern("1444110337152.png").exact()):    
    rightClick(Pattern("1444110337152.png").exact())
else:
    if exists(Pattern("1444110353380.png").exact()):
        rightClick(Pattern("1444110353380.png").exact())
click(Pattern("1444111360956.png").similar(0.92).targetOffset(0,-14))
if exists("1444044224508.png"):
    type("1444044240397.png", "automated justification")
    click("1444044266357.png")
wait("1444044280502.png",10)
type(Pattern("1444111509547.png").targetOffset(83,-6), "automated administer comment") 
click(Pattern("1444111567124.png").targetOffset(-38,-2))
 


