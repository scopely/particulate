#Particulate
Particulate is a lightweight particle-effects library written for Android.

## Gradle
```groovy
compile 'com.scopely:particulate:1.0.0'
```
## Setup
Particulate requires no setup or initialization before use. Particulate's `ParticleDrawable` can be instantiated when needed and used like any other `Drawable`, without modification.

## Usage

###Particle
A `Particle` is the base unit of the Particulate system. Each individual `Particle` contains information about its position, velocity, and the fields in which it is moving, as well as all the information necessary to render an image onto the canvas. You will not have to instantiate a `Particle` yourself, but rather make use of a `ParticleFactory`.

###ParticleFactory
A `ParticleFactory` is a class from which an instance of `Particle` can be requested. The recommended implementation, `ParticleFactoryImpl` can be instantiated with a raw bitmap or drawable; this image will be used as the sprite drawn by the particle.

###Emitter
An `Emitter` is a class that determines where and how a particle is emitted into the world. Emitters are instantiated with a `ParticleFactory` from which individual particles are requested. Emitters also have an `EmissionTarget` set on them: this is the drawing surface onto which particles are emitted. `ParticulateDrawable` is the provided `EmissionTarget`.

`Emitter`s that inherit from the base `EmitterImpl` class have setters for the following pdfs (probability distribution functions) from which a value will be selected to set the appropriate property on each individual particle. 
* speedPdf: controls the magnitude of the emitted particle's velocity vector.
* anglePdf: controls the angle of the emitted particle's velocity vector
* angularSpeedPdf: controls the speed of rotation of the emitted particle
* massPdf: controls the mass of the emitted particle
* chargePdf: controls the electrical charge of the emitted particle

They also have setters for interpolators that alter the value of the appropriate property of the particle throughout its lifetime:
* scaleXInterpolator: alters the scale factor of the sprite in the X dimension.
* scaleYInterpolator: alters the scale factor of the sprite in the Y dimension.
* alphaInterpolator: alters the alpha value of the sprite.

####PDFs
Particulate includes the following built in PDFs:
* `ConstantPdf`: always emits the same value.
* `UniformPdf`: emits a value evenly distributed between a lower and upper bound.
* `GaussianPdf`: emits a value Gaussianly distributed between a lower and upper bound.

####PointEmitter
A `PointEmitter` is an `Emitter` that emits from a single point in space.

####PathEmitter
A `PathEmitter` is an `Emitter` that emits from along a provided `android.graphics.Path`

####LineEmitter
A `LineEmitter` is subclass of `PathEmitter` that creates a straight line `Path` between two provided points.

####TouchPointEmitter
A `TouchPointEmitter` is an `Emitter` that emits from the point at which the user is touching the screen.

###Field
`Field`s alter the velocity of `Particle`s based on the properties of both the `Field` and the `Particle`. Each `Particle` carries a reference to all of the `Field`s in which it exists. These fields can be set invidually, but the recommended method is to set `Field`s on the `EmitterImpl`, which will then set them on all `Particle`s at the time of each `Particle`'s emission. Particulate contains the following standard `Field`s:
* `ConstantForceField`: Applies a constant force vector to the particle.
* `ConstantAccelerationField`: Applies a constant acceleration to the particle. Similar to a `ConstantForceField` except it is scaled to the mass of each individual particle, ensuring all particles accelerate equally. Can be used to simulate a gravity well for example.
* `Friction`: Applies a constant force in the opposite direction of the particle's velocity.
* `GravitationalField`: Initiated with a point mass. Applies a force resulting from the gravitational pull of the point mass on the particle.
* `AirResistance`: Applies a force opposite the direction of the particle's velocity vector, with a magnitude that is a function of the magnitude of the particle's velocity vector.
* `WindField`:  A subclass of `AirResistance` that is initiated with a wind velocity interpolator. The force of the air resistance is then a function of the difference between the particle's velocity and the wind velocity.

###A Note on Design Philosophy
Particulate is designed to prioritize an easy-to-use and extensible API, and hassle free integration with Android's existing UI framework (`Canvas`, `Drawable`, `View`). As a result its performance, while optimized to the degree reasonably possible, does not include the kinds of optimizations that can be achieved through use of the Android NDK, OpenGL or other lower level code. Particulate is designed for adding particle-effect flourishes to Android UI, not for doing the bulk work of heavy physics simulations for applications like games.


