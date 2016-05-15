click("1443677660612.png")

hover("medicationmenuitem.png")

hover("newprescriptionmenu.png")

hover("listsmenu.png")

click("drugmenu.png")


wait("1444032184850.png",20)
waitVanish("1444105010875.png",3)


click(Pattern("1444104387097.png").targetOffset(-9,-10))
      
hover("1444112923981.png")

type(Key.F12,Key.CTRL)

if(exists("1444122950564.png")):
    type("OpenDrugList_hot.cuts")
    click("1444123135834.png")

click("1443677660612-1.png")

hover("medicationmenuitem-1.png")

hover("newprescriptionmenu-1.png")

hover("listsmenu-1.png")

click("drugmenu-1.png")

wait("1444032184850-1.png",20)
waitVanish("1444105010875-1.png",3)


click(Pattern("1444104387097-1.png").targetOffset(-9,-10))
      
hover("1444112923981-1.png")

type(Key.F12,Key.CTRL)

if(exists("1444123222958.png")):
    click("1444123239024.png")