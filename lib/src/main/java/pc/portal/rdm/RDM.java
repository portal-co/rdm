package pc.portal.rdm;

public class RDM {
    public static final boolean scanClasses() {
        for (String nativeString : new String[] { "cyb0124.NativeLoader" }) {

            try {
                Class.forName(nativeString);
                return true;
            } catch (ClassNotFoundException e) {
            }
        }
        ClassLoader loader = RDM.class.getClassLoader();
        if (loader != null) {
            for (String packageString : new String[] { "cyb0124" }) {
                if (loader.getDefinedPackage(packageString) != null) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }
}
