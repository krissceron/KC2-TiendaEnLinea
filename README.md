# 🧭 Guía para Clonar y Configurar un Proyecto OpenXava desde GitHub

Esta guía está dirigida a los colaboradores del proyecto **KC2-TiendaEnLinea** para que puedan clonar el repositorio correctamente y trabajar de forma sincronizada con el equipo.

---

## ✅ Paso 1: Crear una carpeta para los proyectos

Antes de clonar el repositorio, crea una carpeta donde guardarás tus proyectos de OpenXava, por ejemplo:

```bash
C:\openxava-proyectos\
```

---

## ✅ Paso 2: Clonar el repositorio desde GitHub

Abre una terminal (CMD, PowerShell o Git Bash) y escribe lo siguiente:

```bash
cd C:\openxava-proyectos\
git clone https://github.com/krissceron/KC2-TiendaEnLinea.git
```

Esto descargará una carpeta llamada `KC2-TiendaEnLinea` con todo el proyecto desde GitHub.

---

## ✅ Paso 3: Importar el proyecto en OpenXava Studio

1. Abre **OpenXava Studio**
2. Ve a **File > Import**
3. Elige: **Maven > Existing Maven Projects**
4. Clic en **Next**
5. Clic en **Browse** y selecciona la carpeta:

```bash
C:\openxava-proyectos\KC2-TiendaEnLinea
```

6. Asegúrate de que esté marcada la casilla del proyecto
7. Clic en **Finish**

---

## 🔁 Buenas prácticas para trabajar con Git

### 🔄 Antes de comenzar a trabajar (siempre):

```bash
git pull origin main
```

### 💻 Después de hacer tus cambios:

```bash
git add .
git commit -m "Descripción clara del cambio"
git push origin main
```

---

## ⚠️ Notas importantes

- No clones directamente dentro del `workspace` de OpenXava
- Usa `pull` antes de trabajar, y `push` después de tus cambios
- Si hay conflictos, solicita ayuda para resolverlos
- Siempre confirma que estás trabajando en la rama correcta (`main`)
