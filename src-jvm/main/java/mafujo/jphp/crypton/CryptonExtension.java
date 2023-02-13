package mafujo.jphp.crypton;

import mafujo.jphp.crypton.classes.CryptonAES;
import mafujo.jphp.crypton.classes.CryptonRSA;

import mafujo.jphp.crypton.classes.CryptonRSA2;
import php.runtime.annotation.Reflection;
import php.runtime.env.CompileScope;
import php.runtime.ext.support.Extension;


public class CryptonExtension extends Extension
{
    public static final String NS = "mafujo\\jphp\\crypton";

    @Override
    public Status getStatus() { return Status.EXPERIMENTAL; }

    @Override
    public void onRegister(CompileScope compileScope)
    {
        registerClass(compileScope, CryptonRSA.class);
        registerClass(compileScope, CryptonRSA2.class);
        registerClass(compileScope, CryptonAES.class);
    }
}
