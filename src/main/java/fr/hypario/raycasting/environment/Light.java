package fr.hypario.raycasting.environment;

import fr.hypario.raycasting.math.*;

public abstract class Light {
    private final Color color;

    Light(Color color) {
        this.color = color;
    }

    /**
     * Calculate the Phong illumination of a point
     * formula : max(n * h, 0)^shininess * lightColor * specularColor
     *
     * @param p         a point
     * @param eyeDir    the direction of the eye
     * @param normal    the normal of the object
     * @param shininess the shininess of the object
     * @param specular  the specular color of the object
     * @return the illumination of Phong
     */
    public Color getPhongIllumination(Point3D p, Vector3D eyeDir, Vector3D normal, int shininess, Color specular) {
        Vector3D h = this.getLightDir(p).add(eyeDir.normalize()).normalize(); // halfway vector
        double specAngle = Math.max(normal.dot(h), 0); // max(normal * h, 0) which is the spec angle

        double intensity = (shininess > 0) ? Math.pow(specAngle, shininess) : specAngle; // specAngle^shininess intensity of specular light

        return this.color.times(specular).mul(intensity); // intensity * lightcolor * specularColor (Light Blinn-Phong)
    }

    // return light direction
    public abstract Vector3D getLightDir(Point3D p);

    /**
     * calculate color of a given object according to parameters
     *
     * @param world  the scene, used for shadows
     * @param p      the point where a ray intersected with an object
     * @param eyeDir the direction to the eye
     * @param object the intersected object
     * @return the color of the light
     */
    public Color getColor(Scene world, Point3D p, Vector3D eyeDir, BasicObject object) {
        Color color = new Color();
        if (this.isHidden(world, p)) return color;

        double max = Math.max(object.getNormal().dot(this.getLightDir(p)), 0);

        // case without texture
        color = color.add(this.color.mul(max).times(object.getDiffuse()));

        // reflection
        Color ip = this.getPhongIllumination(p, eyeDir, object.getNormal(), object.getShininess(), object.getSpecular());
        return color.add(ip);
    }

    protected boolean isHidden(Scene world, Point3D p) {
        if (world.isShadowEnabled()) {
            IntersectedObject intersected = world.intersect(new Ray(p, this.getLightDir(p)), false);
            return intersected.hasIntersected;
        }
        return false;
    }
}
