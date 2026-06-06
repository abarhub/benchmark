import pytest
from main import initTab,multi_calcul


def test_multi_calcul():
    lenx=3
    tab1=initTab(lenx,False)
    tab2=initTab(lenx,True)
    res=multi_calcul(tab1,tab2,lenx)

    assert 1==1
    assert len(res)==3
    assert egaux(3.3,res[0][0])
    assert egaux(3.42,res[0][1])
    assert egaux(3.3,res[0][2])
    assert egaux(3.21,res[1][0])
    assert egaux(3.3,res[1][1])
    assert egaux(3.21,res[1][2])
    assert egaux(3.3,res[2][0])
    assert egaux(3.42,res[2][1])
    assert egaux(3.3,res[2][2])


def egaux(a,b):
    if abs(a - b) < 0.001:
        return True
    else:
        return False