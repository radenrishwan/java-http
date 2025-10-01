package org.raden.server;

public class Preview {

    public static String html() {
        return """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Fireworks & Datetime</title>
                    <script src="https://cdn.tailwindcss.com"></script>
                    <style>
                        /* For a cleaner look, hide scrollbars */
                        body {
                            overflow: hidden;
                        }
                    </style>
                </head>
                <body class="bg-black flex items-center justify-center min-h-screen">
               \s
                    <canvas id="fireworksCanvas" class="absolute top-0 left-0 w-full h-full z-0"></canvas>
               \s
                    <div id="datetime" class="relative z-10 text-center font-mono text-white">
                        <div id="time" class="text-6xl md:text-8xl font-bold tracking-widest text-shadow-lg"></div>
                        <div id="date" class="text-xl md:text-2xl mt-2 tracking-wider"></div>
                    </div>
               \s
                    <script>
                        // --- DATETIME ANIMATION ---
                        const timeElement = document.getElementById('time');
                        const dateElement = document.getElementById('date');
               \s
                        function updateDateTime() {
                            const now = new Date();
               \s
                            // Format Time (HH:MM:SS) with a little "ticking" effect on the colon
                            const hours = String(now.getHours()).padStart(2, '0');
                            const minutes = String(now.getMinutes()).padStart(2, '0');
                            const seconds = String(now.getSeconds()).padStart(2, '0');
                            const colon = seconds % 2 === 0 ? '<span class="opacity-50">:</span>' : ':';
                            timeElement.innerHTML = `${hours}${colon}${minutes}${colon}${seconds}`;
               \s
                            // Format Date (Day, Month Date, Year)
                            const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' };
                            dateElement.textContent = now.toLocaleDateString('en-US', options);
                        }
               \s
                        // Update the date and time every second
                        setInterval(updateDateTime, 1000);
                        updateDateTime(); // Initial call to display immediately
               \s
                        // --- FIREWORKS ANIMATION ---
                        const canvas = document.getElementById('fireworksCanvas');
                        const ctx = canvas.getContext('2d');
                        canvas.width = window.innerWidth;
                        canvas.height = window.innerHeight;
               \s
                        let particles = [];
                        let fireworks = [];
               \s
                        function randomColor() {
                            const hue = Math.random() * 360;
                            return `hsl(${hue}, 100%, 50%)`;
                        }
               \s
                        // Represents a single firework rocket or an explosion particle
                        class Particle {
                            constructor(x, y, color, velocity) {
                                this.x = x;
                                this.y = y;
                                this.color = color;
                                this.velocity = velocity;
                                this.alpha = 1;
                                this.friction = 0.99;
                                this.gravity = 0.03;
                            }
               \s
                            draw() {
                                ctx.save();
                                ctx.globalAlpha = this.alpha;
                                ctx.beginPath();
                                ctx.arc(this.x, this.y, 2, 0, Math.PI * 2, false);
                                ctx.fillStyle = this.color;
                                ctx.fill();
                                ctx.restore();
                            }
               \s
                            update() {
                                this.velocity.x *= this.friction;
                                this.velocity.y *= this.friction;
                                this.velocity.y += this.gravity;
                                this.x += this.velocity.x;
                                this.y += this.velocity.y;
                                this.alpha -= 0.005;
                                this.draw();
                            }
                        }
               \s
                        // Represents the main firework rocket that explodes
                        class Firework extends Particle {
                             constructor(x, y, color) {
                                super(x, y, color, {\s
                                    x: (Math.random() - 0.5) * 3,\s
                                    y: Math.random() * -12 - 5\s
                                });
                                this.explosionParticles = 100;
                             }
               \s
                             update() {
                                super.update();
                                // When the rocket slows down at its peak, it explodes
                                if (this.velocity.y >= 0) {
                                    this.explode();
                                    this.alpha = 0; // Hide the original rocket
                                }
                             }
               \s
                             explode() {
                                for (let i = 0; i < this.explosionParticles; i++) {
                                    const angle = Math.random() * Math.PI * 2;
                                    const speed = Math.random() * 5;
                                    particles.push(new Particle(this.x, this.y, this.color, {
                                        x: Math.cos(angle) * speed,
                                        y: Math.sin(angle) * speed,
                                    }));
                                }
                             }
                        }
               \s
               \s
                        function animate() {
                            requestAnimationFrame(animate);
                            // Create a trailing effect by drawing a semi-transparent rectangle
                            ctx.fillStyle = 'rgba(0, 0, 0, 0.1)';
                            ctx.fillRect(0, 0, canvas.width, canvas.height);
               \s
                            // Launch new fireworks randomly
                            if (Math.random() < 0.03) {
                                 fireworks.push(new Firework(Math.random() * canvas.width, canvas.height, randomColor()));
                            }
               \s
                            // Update and draw fireworks
                            fireworks.forEach((firework, index) => {
                                if (firework.alpha <= 0) {
                                    fireworks.splice(index, 1);
                                } else {
                                    firework.update();
                                }
                            });
               \s
                            // Update and draw explosion particles
                            particles.forEach((particle, index) => {
                                if (particle.alpha <= 0) {
                                    particles.splice(index, 1);
                                } else {
                                    particle.update();
                                }
                            });
                        }
               \s
                        window.addEventListener('resize', () => {
                             canvas.width = window.innerWidth;
                             canvas.height = window.innerHeight;
                        });
               \s
                        animate();
                    </script>
               \s
                </body>
                </html>
       \s""".trim();
    }
}
